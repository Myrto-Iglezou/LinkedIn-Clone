package com.linkedin.linkedinclone.controllers;


import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.*;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private final NotificationRepository notificationRepository;
    private final ChatRepository chatRepository;

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

        for(User u: searchResults){
            System.out.println(u.getName());
            Picture uPic = u.getProfilePicture();
            if(uPic!=null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), decompressBytes(uPic.getBytes()));
                u.setProfilePicture(temp);
            }
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
        System.out.println("connectionsFollowing");
        for(Connection con: connectionsFollowing) {
            if(con.getIsAccepted()){
                User userinNetwork = con.getUserFollowed();
                System.out.println(userinNetwork.getName());
                network.add(userinNetwork);
            }
        }

        Set<Connection> connectionsFollowedBy = currentUser.getUserFollowedBy();
        System.out.println("connectionsFollowedBy");
        for(Connection con: connectionsFollowedBy) {
            if(con.getIsAccepted()){
                User userinNetwork = con.getUserFollowing();
                System.out.println(userinNetwork.getName());
                network.add(userinNetwork);
            }
        }

        for(User u: network){
            System.out.println(u.getName());
            Picture uPic = u.getProfilePicture();
            if(uPic!=null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), decompressBytes(uPic.getBytes()));
                temp.setCompressed(false);
                u.setProfilePicture(temp);
            }
        }

        return network;
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/request/{otherUserId}")
    public ResponseEntity<String> hasSendRequest(@PathVariable Long id, @PathVariable Long otherUserId) {

        System.out.println("\n\n>Check request");
        User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        User otherUser = userRepository.findById(otherUserId).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        System.out.println(otherUser.getName());

        Set<Connection> connectionsFollowing = currentUser.getUsersFollowing();
        System.out.println("connectionsFollowing");
        for(Connection con: connectionsFollowing) {
            if(!con.getIsAccepted()  && con.getUserFollowed()==otherUser){
                User userinNetwork = con.getUserFollowed();
                System.out.println(userinNetwork.getName());
                return ResponseEntity.ok("true");
            }
        }

        connectionsFollowing = currentUser.getUserFollowedBy();
        System.out.println("----");
        for(Connection con: connectionsFollowing) {
            if(!con.getIsAccepted()  && con.getUserFollowing()==otherUser){
                User userinNetwork = con.getUserFollowing();
                System.out.println(userinNetwork.getName());
                return ResponseEntity.ok("true");
            }
        }
        return ResponseEntity.ok("false");
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

        System.out.println("\n\n\n---------");

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));

        System.out.println(user.getName() + " will accept connection id"+ connectionId);

        Connection conn = connectionRepository.findById(connectionId).orElseThrow(() -> new UserNotFoundException("Notification with id "+id+"doesn't exist"));
        conn.setIsAccepted(true);
        connectionRepository.save(conn);

        Notification not = notificationRepository.findByConnectionId(connectionId).orElseThrow(() -> new UserNotFoundException("Notification with id "+id+"doesn't exist"));
        not.setIsShown(true);
        notificationRepository.save(not);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy:HH.mm.ss");

        Chat chat = new Chat();
        chat.setTimestamp(new Timestamp(System.currentTimeMillis()));
        Set<User> users = new HashSet<>();
        users.add(user);
        if(conn.getUserFollowed()!=user)
            users.add(conn.getUserFollowed());
        else if(conn.getUserFollowing()!=user)
            users.add(conn.getUserFollowing());

        chat.setUsers(users);
        chatRepository.save(chat);
        System.out.println("\n\n\n");
        System.out.println(chat);
        for(User u: users) {
            System.out.println(u);
            System.out.println(u.getChats());}
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
