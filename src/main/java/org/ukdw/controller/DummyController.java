package org.ukdw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukdw.dto.response.ResponseWrapper;
import org.ukdw.entity.StudentEntity;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.services.UserAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DummyController {

    private final UserAccountService userAccountService;

    //dummy sample
    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
//        return ResponseEntity.ok(new ResponseWrapper("Hello from public API"));
//        List<UserAccountEntity> users = userAccountService.listUserAccount();
//        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), users));

//        StudentEntity studentEntity = new StudentEntity();
//        studentEntity.setEmail("teststudent@mail.com");
//        studentEntity.setPassword("Test");
//        studentEntity.setName("Test");
//        studentEntity.setStudentId("std256");
//        userAccountService.createUserAccount(studentEntity);
        List<UserAccountEntity> users = userAccountService.listUserAccount();
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), users));
    }

    //dummy sample
    @GetMapping("/restricted")
    public ResponseEntity<?> restricted() {
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Hello from restricted API"));
    }

    @PreAuthorize("hasRole(T(org.ukdw.entity.AuthoritiesConstants).ROLE_ADMIN.name())")
    @GetMapping("/adminonly")
    public ResponseEntity<?> adminonly() {
        ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Hello from admin only API");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(org.ukdw.entity.AuthoritiesConstants).ROLE_TEACHER.name())")
    @GetMapping("/teacheronly")
    public ResponseEntity<?> teacheronly() {
        ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Hello from teacher only API");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(org.ukdw.entity.AuthoritiesConstants).ROLE_STUDENT.name())")
    @GetMapping("/studentonly")
    public ResponseEntity<?> studentonly() {
        ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Hello from student only API");
        return ResponseEntity.ok(response);
    }
}