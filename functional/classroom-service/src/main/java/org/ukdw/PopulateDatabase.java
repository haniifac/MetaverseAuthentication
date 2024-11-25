package org.ukdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ukdw.classroom.entity.AttendanceEntity;
import org.ukdw.classroom.entity.AttendanceRecord;
import org.ukdw.classroom.entity.ClassroomEntity;
import org.ukdw.classroom.repository.AttendanceRepository;
import org.ukdw.classroom.repository.ClassroomRepository;
import org.ukdw.classroom.service.implementation.ClassroomServiceImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Component
public class PopulateDatabase implements CommandLineRunner {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ClassroomServiceImpl classroomServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        if (classroomRepository.count() == 0) {
            ClassroomEntity mathClassroom = new ClassroomEntity();
            mathClassroom.setName("Math Classroom");
            mathClassroom.setDescription("Kelas Univ Indonesia, Membahas tentang blablabla");
            mathClassroom.setSemester("Genap");
            mathClassroom.setTahunAjaran("2024/2025");
            mathClassroom.setTeacherIds(Set.of(1L, 2L));  // Example teacher IDs
            mathClassroom.setStudentIds(Set.of(10L, 11L, 12L));  // Example student IDs
            ClassroomEntity savedClassroom = classroomServiceImpl.createClassroom(mathClassroom);

            Instant currentTime = Instant.now();

            AttendanceEntity mathAttendance = new AttendanceEntity();
            mathAttendance.setClassroom(savedClassroom);
            mathAttendance.setOpenTime(currentTime.minus(4, ChronoUnit.HOURS));
            mathAttendance.setCloseTime(currentTime.plus(4, ChronoUnit.HOURS));

            // Example of students' attendance times
            Set<AttendanceRecord> records = new HashSet<>();
            AttendanceRecord record1 = new AttendanceRecord(10L, currentTime.minus(30, ChronoUnit.MINUTES));
            record1.setAttendance(mathAttendance);  // Set the reference to the parent AttendanceEntity

//            AttendanceRecord record2 = new AttendanceRecord(11L, currentTime.minus(20, ChronoUnit.MINUTES));
//            record2.setAttendance(mathAttendance);  // Set the reference to the parent AttendanceEntity

            records.add(record1);
//            records.add(record2);

            // Set the records to the attendance entity
            mathAttendance.setRecords(records);

            // Save attendance record
            attendanceRepository.save(mathAttendance);

            System.out.println("Database has been populated with initial data.");

        } else {
            System.out.println("Classroom already exist, skipping insert.");
        }
    }
}
