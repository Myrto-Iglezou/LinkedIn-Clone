package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.dto.NetworkUserDTO;
import com.linkedin.linkedinclone.dto.NotificationsDTO;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.ConnectionRepository;
import com.linkedin.linkedinclone.repositories.NotificationRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

import static com.linkedin.linkedinclone.enumerations.NotificationType.*;

@RestController
@AllArgsConstructor
public class NotificationController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConnectionRepository connectionRepository;
    private final NotificationRepository notificationRepository;

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/notifications")
    public Set<Notification> getNotifications(@PathVariable Long id) {
        System.out.println("\n\n\n>GET notifications");
        User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Set<Notification> notificationsActive = new HashSet<>();
        for(Notification not: currentUser.getNotifications()){
            System.out.println(not);
            if(!not.getIsShown() && not.getType() == COMMENT ) {
                notificationsActive.add(not);
            } else if (!not.getIsShown() && not.getType() == INTEREST) {
                notificationsActive.add(not);
            } else if (!not.getIsShown() && !not.getConnection_request().getIsAccepted() && not.getType() == CONNECTION_REQUEST) {
                notificationsActive.add(not);
            } else;
        }

        return notificationsActive;
    }
}
