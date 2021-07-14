package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signup(@RequestPart("object") User user, @RequestPart("imageFile") MultipartFile file) throws IOException {

    }



    }
