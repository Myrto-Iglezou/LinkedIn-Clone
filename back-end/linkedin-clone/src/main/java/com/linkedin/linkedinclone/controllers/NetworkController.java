package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.dto.NetworkUserDTO;
import com.linkedin.linkedinclone.dto.NewUserInfo;
import com.linkedin.linkedinclone.dto.UserNetworkDTO;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.InterestReaction;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/network")
    public Set<NetworkUserDTO> getNetwork(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        Set<NetworkUserDTO> network = new HashSet<>();
        Set<Connection> connections = currentUser.getUsersFollowing();
        for(Connection con: connections) {
            User userinNetwork = con.getUserFollowing();
            NetworkUserDTO netuser = new NetworkUserDTO(userinNetwork.getId(),userinNetwork.getName(),userinNetwork.getSurname(),userinNetwork.getCurrentJob(),userinNetwork.getCurrentCompany());
            network.add(netuser);
        }

        return network;
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("/in/{id}/new-connection/{newUserId}")
    public ResponseEntity addToConnections(@PathVariable Long id,@PathVariable Long newUserId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        userService.newConnection(user,newUserId);


        return ResponseEntity.ok("\"New connection added with success!\"");
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/network/users/{userShownId}")
    public UserProfileDTO getNetwork(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());


        return network;
    }


}
