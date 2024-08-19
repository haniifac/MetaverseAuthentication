package com.virtualclassroom.authrestapi.services;

import com.virtualclassroom.authrestapi.dto.SignUpRequest;
import com.virtualclassroom.authrestapi.entities.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
}
