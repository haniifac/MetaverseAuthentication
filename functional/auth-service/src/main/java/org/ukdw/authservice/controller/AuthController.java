package org.ukdw.authservice.controller;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.ukdw.authservice.dto.*;
import org.ukdw.common.ResponseWrapper;
import org.ukdw.authservice.entity.UserAccountEntity;
import org.ukdw.authservice.service.AuthService;

import java.text.ParseException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @ResponseBody
    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signin(@RequestBody SignInRequest request) {
        UserAccountEntity userEntity = authService.signIn(request.getEmail(), request.getPassword());
        ResponseWrapper<UserAccountEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), userEntity);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        UserAccountEntity userEntity = authService.signUp(request);
        ResponseWrapper<UserAccountEntity> response = new ResponseWrapper<>(HttpStatus.OK.value(), userEntity);
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(value = "/apps-check-permission", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> appsCheckPermission(@Valid @RequestBody AppsCheckPermissionRequest request) {
        ResponseWrapper<AppsCheckPermissionResponse> response = new ResponseWrapper<>(HttpStatus.OK.value(),
                AppsCheckPermissionResponse.builder()
                        .status(authService.canAccessFeature(request.getFeatureCode()))
                        .build());
        return ResponseEntity.ok(response);
    }

    @ResponseBody
    @PostMapping(value = "/check-permission")
    public ResponseEntity<?> appsCheckPermission(@Valid @RequestBody CheckPermissionRequest checkPermissionRequest, HttpServletRequest request) {
        var response = AppsCheckPermissionResponse.builder()
                .status(authService.canAccessFeature(checkPermissionRequest.getRoles(), checkPermissionRequest.getPermissions(), request))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(new ResponseWrapper<>(HttpStatus.BAD_REQUEST.value(), "Invalid token"));
        }

        String token = authHeader.substring(7);
        try {
            var verifyTokenDto = authService.isTokenValidAndNotExpired(token);
            return ResponseEntity.ok(new ResponseWrapper<>(HttpStatus.OK.value(), verifyTokenDto));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseWrapper<>(e.getStatusCode().value(), e.getReason()));
        }
    }

    @ResponseBody
    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto request) {
        try {
            String newAccessToken = authService.refreshAccessToken(request.getRefreshToken());
            ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.OK.value(), newAccessToken);
            return ResponseEntity.ok(response);
        } catch (ParseException | JwtException e) {
            ResponseWrapper<String> response = new ResponseWrapper<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
