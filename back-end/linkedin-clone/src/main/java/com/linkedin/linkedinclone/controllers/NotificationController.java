package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.dto.NetworkUserDTO;
import com.linkedin.linkedinclone.dto.NotificationsDTO;
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
    public NotificationsDTO getNotifications(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());
        Set<NetworkUserDTO> usersPending = new HashSet<>();
        Set<InterestReaction> interestReactions = new HashSet<>();
        Set<Comment> comments = new HashSet<>();

        for(Notification not: currentUser.getNotifications()){
            if(!not.getIsShown() && not.getType() == COMMENT ) {
                Connection con = not.getConnection_request();
                if(!con.getIsAccepted()){
                    User userinNetwork = con.getUserFollowing();
                    usersPending.add(new NetworkUserDTO(userinNetwork.getId(),userinNetwork.getName(),userinNetwork.getSurname(),userinNetwork.getCurrentJob(),userinNetwork.getCurrentCompany()));
                }
            } else if (!not.getIsShown() && not.getType() == INTEREST) {
                interestReactions.add(not.getNew_interest());
            } else if (!not.getIsShown() && not.getType() == CONNECTION_REQUEST) {
                comments.add(not.getNew_comment());
            } else;
        }

        return new NotificationsDTO(usersPending,interestReactions,comments);
    }
}
