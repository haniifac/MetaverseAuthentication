package org.ukdw.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukdw.authservice.dto.UserPermissionRequest;
import org.ukdw.authservice.service.UserGroupService;
import org.ukdw.common.ResponseWrapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserGroupService userGroupService;

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN', 511L)")
    @ResponseBody
    @PostMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUserGroup(@Valid @RequestBody UserPermissionRequest request){
        ResponseWrapper<?> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Success adding group to user", userGroupService.addUserGroup(request.getUserId(), request.getGroupId()));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@privilegeVerifierService.hasPrivilege('ADMIN', 511L)")
    @ResponseBody
    @DeleteMapping(value = "/permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeUserGroup(@Valid @RequestBody UserPermissionRequest request){
        ResponseWrapper<?> response = new ResponseWrapper<>(HttpStatus.OK.value(), "Success removed group from user",userGroupService.removeUserGroup(request.getUserId(), request.getGroupId()));
        return ResponseEntity.ok(response);
    }
}
