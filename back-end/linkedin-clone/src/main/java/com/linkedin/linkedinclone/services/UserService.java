package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addConnection(User user,Long newConnectionId) {
        User newConnection = userRepository.findById(newConnectionId).orElseThrow(()-> new UserNotFoundException("Id: "+newConnectionId));
        Set<User> connectedUsers = user.getUsersConnectedWith();
        connectedUsers.add(newConnection);
        user.setUsersConnectedWith(connectedUsers);
        userRepository.save(user);

        connectedUsers = newConnection.getUsersConnectedWith();
        connectedUsers.add(user);
        newConnection.setUsersConnectedWith(connectedUsers);
        userRepository.save(newConnection);
    }

}
