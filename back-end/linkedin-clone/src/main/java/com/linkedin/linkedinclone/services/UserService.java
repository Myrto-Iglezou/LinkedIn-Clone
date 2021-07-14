package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean emailExist(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) { return true; }
        return false;
    }

    public User findUser(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user != null) { return user; }
        return null;
    }

    public Optional<User> findUserByID(Long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found")));
        if (user.isPresent()) {  return user; }
        return null;
    }




}
