package org.ukdw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukdw.dto.request.auth.SignUpRequest;
import org.ukdw.dto.response.ResponseWrapper;
import org.ukdw.entity.UserAccountEntity;
import org.ukdw.services.UserAccountService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserAccountService userAccountService;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(){
        List<UserAccountEntity> users = userAccountService.listUserAccount();
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long id){
        UserAccountEntity user = userAccountService.findUserAccountById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") Long id, @RequestBody SignUpRequest request) {
        boolean isUpdated = userAccountService.updateUserAccount(id, request);
        if(isUpdated){
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Success update user: "+id, ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResponseWrapper<>(HttpStatus.OK.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ""));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id){
        boolean isDeleted = userAccountService.deleteUserAccount(id);
        if(isDeleted){
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), "Success delete user: "+id, ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ResponseWrapper<>(HttpStatus.OK.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ""));
    }
}
