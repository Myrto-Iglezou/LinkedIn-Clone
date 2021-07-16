package com.linkedin.linkedinclone.controllers;


import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@AllArgsConstructor
public class FeedController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/feed")
    public Set<Post> getFeed(@PathVariable Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Set<Post> feedPosts = new HashSet<>();

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        // Posts made by user
        feedPosts.addAll(currentUser.getPosts());

        // Posts from users connections
        Set<User> connectedUsers = currentUser.getUsersConnectedWith();
        for(User u: connectedUsers) {
            feedPosts.addAll(u.getPosts());
        }

        // Posts from connections that are interested

        return feedPosts;
    }
}
