package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @CrossOrigin(origins = "*") // CrossOrigin: For connecting with angular
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> all() {
        List<User> users = userRepository.findAll();
        return users;
    }



}
