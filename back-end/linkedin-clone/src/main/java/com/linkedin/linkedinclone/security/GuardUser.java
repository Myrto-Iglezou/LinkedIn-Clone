package com.linkedin.linkedinclone.security;


import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class GuardUser {

    @Autowired
    UserRepository userRepository;

    public boolean checkUserId(Authentication authentication, int id) {
        String name = authentication.getName();
        User result = userRepository.findUserByUsername(name);
        return result != null && result.getId() == id;
    }
}
