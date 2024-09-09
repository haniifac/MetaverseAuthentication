package com.virtualclassroom.authrestapi.services;

import com.virtualclassroom.authrestapi.dto.JwtAuthenticationResponse;
import com.virtualclassroom.authrestapi.dto.RefreshTokenRequest;
import com.virtualclassroom.authrestapi.dto.SignUpRequest;
import com.virtualclassroom.authrestapi.dto.SigninRequest;
import com.virtualclassroom.authrestapi.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
