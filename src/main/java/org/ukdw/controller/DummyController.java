package org.ukdw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukdw.dto.response.ResponseWrapper;
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
        List<UserAccountEntity> users = userAccountService.listUserAccount();
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), users));
    }

    //dummy sample
    @GetMapping("/restricted")
    public ResponseEntity<?> restricted() {
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Hello from restricted API"));
    }

//    @ResponseBody
//    @PostMapping(value = "/check-access")
//    public ResponseEntity<?> checkAccess(@RequestBody CheckAccessRequest request) {
//        boolean hasAccess = accessControlService.canAccessResource(request.getUsername(), request.getPermissionId());
//        if (hasAccess) {
//            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), String.format("User '%s' permitted to access classroom.",request.getUsername()), ""));
//        } else {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(new ResponseWrapper<>(HttpStatus.FORBIDDEN.value(), String.format("User '%s' forbidden to access classroom.", request.getUsername()), ""));
//        }
//    }

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