package com.linkedin.linkedinclone.controllers;


import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    private final BCryptPasswordEncoder encoder;

    @CrossOrigin(origins = "*") // CrossOrigin: For connecting with angular
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public List<User> all() {
        return userRepository.findAll();
    }


}
