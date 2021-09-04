package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.dto.NetworkUserDTO;
import com.linkedin.linkedinclone.dto.PostDTO;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.linkedin.linkedinclone.enumerations.NotificationType.*;
import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final ConnectionRepository connectionRepository;
    private final InterestReactionRepository interestReactionRepository;


    /* --------- NETWORK --------- */

    public void newConnection(User user,Long userFollowingId) {
        System.out.println(user.getId()+" wants to connect with "+userFollowingId);
        User userToBeFollowed = userRepository.findById(userFollowingId).orElseThrow(()-> new UserNotFoundException("Id: "+userFollowingId));
        Connection newConnection = new Connection(user,userToBeFollowed);
        Notification notification = new Notification(CONNECTION_REQUEST,userToBeFollowed,newConnection);

        System.out.println("Save connection");
        System.out.println(notification);
        System.out.println(newConnection);
        notificationRepository.save(notification);
        connectionRepository.save(newConnection);
    }

    /* --------- FEED --------- */

    public void newPost(User user, Post newPost) {

        newPost.setOwner(user);
        postRepository.save(newPost);
    }

    public void newPostInterested(User user, Post post) {
        InterestReaction newReaction = new InterestReaction(user,post);
        User postOwner = post.getOwner();
        if(postOwner!=user){
            Notification notification = new Notification(INTEREST,postOwner,newReaction);
            notificationRepository.save(notification);
        }
        interestReactionRepository.save(newReaction);
    }

    public void newPostComment(User user, Post post, Comment comment) {
        comment.setUserMadeBy(user);
        comment.setPost(post);
        User postOwner = post.getOwner();
        if(postOwner!=user){
            Notification notification = new Notification(COMMENT,postOwner,comment);
            notificationRepository.save(notification);
        }
        commentRepository.save(comment);
    }

    public Set<User> getUserNetwork(User currentUser) {
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
                u.setProfilePicture(temp);
            }
        }

        return network;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Integer hasApplied(User u,Job j){
        for(Job jj: u.getJobApplied()){
            if(jj==j)
                return 1;
        }
        return -1;
    }

}

