package com.linkedin.linkedinclone.controllers;


import com.intellij.util.containers.MultiMap;
import com.linkedin.linkedinclone.dto.NetworkUserDTO;
import com.linkedin.linkedinclone.dto.NewUserInfo;
import com.linkedin.linkedinclone.dto.UserNetworkDTO;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.InterestReaction;
import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.ConnectionRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import com.linkedin.linkedinclone.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.google.common.collect.Lists.reverse;
import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;
import static com.linkedin.linkedinclone.utils.Utils.minDistance;

@RestController
@AllArgsConstructor
public class NetworkController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConnectionRepository connectionRepository;

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/search/{searchQuery}")
    public List<User> search(@PathVariable Long id,@PathVariable String searchQuery) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        List<User> searchResults = new ArrayList<User>();
        List<User> allUsers = userService.getAllUsers();
        String[] searchQueries = searchQuery.split("\\W+");
        MultiValueMap<Integer,User> map = new LinkedMultiValueMap<>();

        for(String s: searchQueries) {
            String w = s.toLowerCase();
            System.out.println(">>> Lemma: " + s);

            for(User u: allUsers){
                if((u.getId() != id) && (!u.getName().equals("admin"))){
                    int dist;
                    System.out.println(u.getName());
                    if ( (dist = minDistance(w, u.getName().toLowerCase(Locale.ROOT))) < 10){
                        System.out.println("- "+u.getName()+" "+dist);
                        map.add(dist, u);
                    } else if ((dist = minDistance(w,u.getSurname().toLowerCase(Locale.ROOT))) < 10) {
                        System.out.println("- "+u.getSurname()+" "+dist);
                        map.add(dist,u);
                    }else if( (u.getCurrentCompany()!=null && u.getCurrentCompany().toLowerCase(Locale.ROOT) == w) ||  (u.getCurrentJob()!=null && u.getCurrentJob().toLowerCase(Locale.ROOT) == w)) {
                        map.add(1,u);
                    }
                }
            }
        }
        for(Map.Entry m: map.entrySet()){
            System.out.println("-- "+m.getValue());
            searchResults.addAll((List<User>)  m.getValue());
        }

        return reverse(searchResults);
    }

        @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/network")
    public Set<User> getNetwork(@PathVariable Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Set<User> network = new HashSet<>();

        Set<Connection> connectionsFollowing = currentUser.getUsersFollowing();
        for(Connection con: connectionsFollowing) {
            if(con.getIsAccepted()){
                User userinNetwork = con.getUserFollowing();
                network.add(userinNetwork);
            }
        }

        Set<Connection> connectionsFollowedBy = currentUser.getUserFollowedBy();
        for(Connection con: connectionsFollowedBy) {
            if(con.getIsAccepted()){
                User userinNetwork = con.getUserFollowed();
                network.add(userinNetwork);
            }
        }


        for(User user: network){
            Picture pic = user.getProfilePicture();
            if(pic != null){
                Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
                user.setProfilePicture(tempPicture);
            }
        }

        return network;
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("/in/{id}/new-connection/{newUserId}")
    public ResponseEntity addToConnections(@PathVariable Long id,@PathVariable Long newUserId) {

        System.out.println("New connection request:");
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));

        userService.newConnection(user,newUserId);

        System.out.println("New connection added with success!");

        return ResponseEntity.ok("\"New connection added with success!\"");
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("/in/{id}/notifications/{connectionId}/accept-connection")
    public ResponseEntity acceptConnection(@PathVariable Long id,@PathVariable Long connectionId) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));

        Connection conn = connectionRepository.findById(connectionId).orElseThrow(() -> new UserNotFoundException("Notification with id "+id+"doesn't exist"));
        conn.setIsAccepted(true);
        connectionRepository.save(conn);

        return ResponseEntity.ok("\"Connection accepted with success!\"");
    }

/*    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/network/users/{userShownId}")
    public UserProfileDTO getNetwork(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());


        return network;
    }*/


}
