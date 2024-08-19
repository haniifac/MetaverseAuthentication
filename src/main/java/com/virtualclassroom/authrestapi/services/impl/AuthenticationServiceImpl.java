package com.virtualclassroom.authrestapi.services.impl;

import com.virtualclassroom.authrestapi.dto.SignUpRequest;
import com.virtualclassroom.authrestapi.entities.Role;
import com.virtualclassroom.authrestapi.entities.User;
import com.virtualclassroom.authrestapi.repository.UserRepository;
import com.virtualclassroom.authrestapi.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstname(signUpRequest.getFirstName());
        user.setSecondname(signUpRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);


    }
}
