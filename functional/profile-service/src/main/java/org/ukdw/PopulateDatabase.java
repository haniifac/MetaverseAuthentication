package org.ukdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.authservice.entity.StudentEntity;
import org.ukdw.authservice.entity.TeacherEntity;
import org.ukdw.authservice.repository.StudentRepository;
import org.ukdw.authservice.repository.TeacherRepository;

import java.util.List;


@Component
public class PopulateDatabase implements CommandLineRunner {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Insert group data first
        if (studentRepository.count() == 0) {
            createInitialStudents();
            System.out.println("Default students inserted successfully.");
        } else {
            System.out.println("students already exist, skipping insert.");
        }

        // Insert user data with associated group entities
        if (teacherRepository.count() == 0) {
            createInitialTeachers();
            System.out.println("Default teachers inserted successfully.");
        } else {
            System.out.println("Users teachers exist, skipping insert.");
        }
    }

    private void createInitialStudents() {
        // Sample user data
        StudentEntity student1 = new StudentEntity(
                1,
                "Student 1",
                "UKDW",
                "712000001",
                "081255555555",
                "Jl.UKDW",
                "Yogya",
                "Jawa Tengah",
                "Indonesia",
                "71501",
                "MALE"
        );

        studentRepository.saveAll(List.of(student1));
    }

    private void createInitialTeachers(){
        // Sample user data
        TeacherEntity teacher1 = new TeacherEntity(
                3,
                "Teacher 1",
                "UKDW",
                "712000001",
                "081255555555",
                "Jl.UKDW",
                "Yogya",
                "Jawa Tengah",
                "Indonesia",
                "71501",
                "MALE",
                "www.google-scholar.com"
        );

        teacherRepository.saveAll(List.of(teacher1));

    }
}