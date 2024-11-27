package org.ukdw.classroom.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.ukdw.classroom.client.UserClient;
import org.ukdw.classroom.dto.client.FindUserByIdRequest;
import org.ukdw.classroom.dto.client.FindUserByIdResponse;
import org.ukdw.common.exception.RequestParameterErrorException;
import org.ukdw.common.exception.ResourceNotFoundException;
import org.ukdw.classroom.dto.request.UpdateClassroomRequest;
import org.ukdw.classroom.entity.AttendanceEntity;
import org.ukdw.classroom.entity.ClassroomEntity;
import org.ukdw.classroom.repository.AttendanceRepository;
import org.ukdw.classroom.repository.ClassroomRepository;
import org.ukdw.classroom.service.AttendanceService;
import org.ukdw.classroom.service.ClassroomService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    @Lazy
    private AttendanceService attendanceService;

    @Autowired
    private UserClient userClient;

    // CRUD operations for Classroom
    @Override
    public ClassroomEntity createClassroom(ClassroomEntity classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public List<ClassroomEntity> getAllClassroom() {
        return classroomRepository.findAll();
    }

    @Override
    public Optional<ClassroomEntity> getClassroomById(Long classroomId) {
        return classroomRepository.findById(classroomId);
    }

    @Override
    public ClassroomEntity updateClassroom(Long classroomId, UpdateClassroomRequest updatedClassroom) {
        ClassroomEntity classroomEntity = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id " + classroomId));

        if(updatedClassroom.getName() != null){
            classroomEntity.setName(updatedClassroom.getName());
        }

        if (updatedClassroom.getDescription() != null){
            classroomEntity.setDescription(updatedClassroom.getDescription());
        }

        if (updatedClassroom.getTahunAjaran() != null){
            classroomEntity.setTahunAjaran(updatedClassroom.getTahunAjaran());
        }

        if (updatedClassroom.getSemester() != null){
            classroomEntity.setSemester(updatedClassroom.getSemester());
        }

        if (updatedClassroom.getTeacherIds() != null){
            classroomEntity.setTeacherIds(updatedClassroom.getTeacherIds());
        }

        if (updatedClassroom.getStudentIds() != null){
            classroomEntity.setStudentIds(updatedClassroom.getStudentIds());
        }

        return classroomRepository.save(classroomEntity);
    }

    @Override
    public void deleteClassroom(Long classroomId) {
        Optional<ClassroomEntity> classroomOpt = classroomRepository.findById(classroomId);
        Optional<List<AttendanceEntity>> attendanceOpt = attendanceRepository.findByClassroomId(classroomId);
        if (classroomOpt.isPresent()) {
            if (attendanceOpt.isPresent()){
                attendanceService.deleteAttendancesByClassroomId(classroomId);
            }
            classroomRepository.deleteById(classroomId);
        } else {
            throw new ResourceNotFoundException("Classroom not found with id " + classroomId);
        }
    }

    public boolean isStudentEnrolled(Long classroomId, Long studentId){
        Optional<ClassroomEntity> classroomOpt = classroomRepository.findById(classroomId);

        if(classroomOpt.isEmpty()){
            throw new ResourceNotFoundException("Classroom not found with id " + classroomId);
        }

        ClassroomEntity classroom = classroomOpt.get();
        return classroom.getStudentIds()
                .stream()
                .anyMatch(id -> id.equals(studentId));
    }

    public boolean addTeacherToClassroom(Long classroomId, Long teacherId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        if (classroom.getTeacherIds() == null) {
            classroom.setTeacherIds(new HashSet<>());
        }

        if (!classroom.getTeacherIds().add(teacherId)) {
            throw new RequestParameterErrorException("Teacher already exists in the classroom");
        }

        if(!this.isUserExist(teacherId)){
            throw new ResourceNotFoundException("User not found with id: "+ teacherId);
        }

        classroom.getTeacherIds().add(teacherId);
        classroomRepository.save(classroom);
        return true;
    }

    public boolean removeTeacherFromClassroom(Long classroomId, Long teacherId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        if (classroom.getTeacherIds() == null || !classroom.getTeacherIds().remove(teacherId)) {
            throw new RequestParameterErrorException("Teacher does not exist in the classroom");
        }

        classroom.getTeacherIds().remove(teacherId);
        classroomRepository.save(classroom);
        return true;
    }

    public boolean addStudentToClassroom(Long classroomId, Long studentId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        if (classroom.getStudentIds() == null) {
            classroom.setStudentIds(new HashSet<>());
        }

        if (!classroom.getStudentIds().add(studentId)) {
            throw new RequestParameterErrorException("Student already exists in the classroom");
        }

        if(!this.isUserExist(studentId)){
            throw new ResourceNotFoundException("User not found with id: "+ studentId);
        }

        classroom.getStudentIds().add(studentId);
        classroomRepository.save(classroom);
        return true;
    }

    public boolean removeStudentFromClassroom(Long classroomId, Long studentId) {
        ClassroomEntity classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found"));

        if (classroom.getStudentIds() == null || !classroom.getStudentIds().remove(studentId)) {
            throw new RequestParameterErrorException("Student does not exist in the classroom");
        }

        classroom.getStudentIds().remove(studentId);
        classroomRepository.save(classroom);
        return true;
    }

    private boolean isUserExist(Long id){
        try{
            log.info("checking if user exist with id {}", id);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            FindUserByIdRequest req = new FindUserByIdRequest(id);
            var response = userClient.findUserExistbyId(req, request);

            ObjectMapper mapper = new ObjectMapper();
            var convert = mapper.convertValue(response,  FindUserByIdResponse.class);

            log.info("Response: {}", convert.getMessage());
            return convert.isUserExist();
        }catch (Exception e) {
            log.info("failed to check user exist: {}", e.getMessage());
            return false;
        }
    }
}