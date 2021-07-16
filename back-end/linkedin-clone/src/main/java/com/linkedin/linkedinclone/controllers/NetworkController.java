package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.dto.NewUserInfo;
import com.linkedin.linkedinclone.dto.UserNetworkDTO;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
public class NetworkController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/network")
    public Set<User> getUsersNetwork() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        Set<User> usersSet = new HashSet<>();

        return user.getUsersConnectedWith();
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("/add-connection/{newUserId}")
    public ResponseEntity addToConnections(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        User newConnection = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Id: "+id));
        Set<User> connectedUsers = user.getUsersConnectedWith();
        connectedUsers.add(newConnection);
        user.setUsersConnectedWith(connectedUsers);
        userRepository.save(user);

        return ResponseEntity.ok("\"New connection added with success!\"");
    }



}
