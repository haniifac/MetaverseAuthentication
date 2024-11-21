package org.ukdw.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @GetMapping
    public String getProfile() {
        return "Profile Service Is Running";
    }

    @PostMapping
    public String postProfile() {
        return "Profile Service Is Running";
    }
}
