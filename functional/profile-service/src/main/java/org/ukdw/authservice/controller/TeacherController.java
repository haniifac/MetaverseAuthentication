package org.ukdw.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukdw.authservice.entity.TeacherEntity;
import org.ukdw.authservice.service.TeacherService;

import java.util.Optional;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN,TEACHER', 511L, 3L)")
    @GetMapping
    public ResponseEntity<?> getAllStudents(Pageable pageable) {
        return new ResponseEntity<>(teacherService.findAll(pageable), HttpStatus.OK);
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN,TEACHER', 511L, 3L)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return Optional.ofNullable(teacherService.findById(id))
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody TeacherEntity teacherEntity) {
        return new ResponseEntity<>(teacherService.save(teacherEntity), HttpStatus.CREATED);
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN,TEACHER', 511L, 3L)")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, TeacherEntity teacherEntity) {
        return new ResponseEntity<>(teacherService.update(id, teacherEntity), HttpStatus.OK);
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN,TEACHER', 511L, 3L)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        teacherService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
