package com.virtualclassroom.authrestapi.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{

    UserDetailsService userDetailsService();
}
