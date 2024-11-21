package org.ukdw.classroom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukdw.common.ResponseWrapper;
import org.ukdw.common.exception.ResourceNotFoundException;
import org.ukdw.classroom.dto.classroom.ClassroomPublicDTO;
import org.ukdw.classroom.dto.request.UpdateClassroomRequest;
import org.ukdw.classroom.entity.ClassroomEntity;
import org.ukdw.classroom.service.implementation.ClassroomServiceImpl;
import org.ukdw.classroom.util.ClassroomMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomServiceImpl classroomServiceImpl;

    @PostMapping
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('TEACHER,ADMIN', 3L, 511L)")
    public ResponseEntity<ClassroomEntity> createClassroom(@RequestBody ClassroomEntity classroom) {
        ClassroomEntity createdClassroom = classroomServiceImpl.createClassroom(classroom);
        return new ResponseEntity<>(createdClassroom, HttpStatus.CREATED);
    }

    @GetMapping()
    @PreAuthorize("@privilegeVerifierService.hasPrivilege('TEACHER,ADMIN,STUDENT', 3L, 511L, 1L)")
    public ResponseEntity<?> getAllClassroom() {
        List<ClassroomEntity> classrooms = classroomServiceImpl.getAllClassroom();
        List<ClassroomPublicDTO> dtoList = classrooms.stream()
                .map(ClassroomMapper::toPublicDTO)
                .collect(Collectors.toList());

        ResponseWrapper<List<ClassroomPublicDTO>> response = new ResponseWrapper<>(HttpStatus.OK.value(), dtoList);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('TEACHER,ADMIN,STUDENT', 3L, 511L, 1L)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getClassroomById(@PathVariable Long id) {
        Optional<ClassroomEntity> classroom = classroomServiceImpl.getClassroomById(id);
        return classroom.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with id:" + id));
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN,TEACHER', 511L, 3L)")
    @PutMapping("/{id}")
    public ResponseEntity<ClassroomEntity> updateClassroom(@PathVariable Long id, @RequestBody UpdateClassroomRequest request) {
        ClassroomEntity updated = classroomServiceImpl.updateClassroom(id, request);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN,TEACHER', 511L, 3L)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        classroomServiceImpl.deleteClassroom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Attendance system
//    @PostMapping("/{classroomId}/attendance")
//    public ResponseEntity<String> recordAttendance(
//            @PathVariable Long classroomId,
//            @RequestParam Long studentId,
//            @RequestHeader("Authorization") String jwtToken) {
//        String result = classroomService.recordAttendance(classroomId, studentId, jwtToken);
//        if ("Attendance recorded successfully".equals(result)) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
//        }
//    }
}
