package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.dto.NetworkUserDTO;
import com.linkedin.linkedinclone.dto.PostDTO;
import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.*;
import com.linkedin.linkedinclone.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

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

    public Set<Post> getFeedPosts(User user) {
        Set<Post> feedPosts = new HashSet<>();
        feedPosts.addAll(user.getPosts());


        Set<Connection> connections = user.getUsersFollowing();
        for(Connection con: connections) {
            if(con.getIsAccepted()){
                User userFollowing = con.getUserFollowed();
                feedPosts.addAll(userFollowing.getPosts());

                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

                for(InterestReaction ir: interestReactions){
                    feedPosts.add(ir.getPost());
                }
            }
        }

        connections = user.getUserFollowedBy();
        for(Connection con: connections) {
            if(con.getIsAccepted()){
                User userFollowing = con.getUserFollowing();
                feedPosts.addAll(userFollowing.getPosts());

                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

                for(InterestReaction ir: interestReactions){
                    feedPosts.add(ir.getPost());
                }
            }
        }

        for(Post p: feedPosts) {
            User owner = p.getOwner();

            Picture pic = owner.getProfilePicture();
            if(pic != null){
                if(pic.isCompressed()){
                    Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
                    pic.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }

            Set<Comment> comments = p.getComments();
            for(Comment c: comments){
                User commentOwner = c.getUserMadeBy();
                Picture cpic = commentOwner.getProfilePicture();
                if(cpic != null){
                    if(cpic.isCompressed()){
                        Picture tempPicture = new Picture(cpic.getId(),cpic.getName(),cpic.getType(),decompressBytes(cpic.getBytes()));
                        cpic.setCompressed(false);
                        commentOwner.setProfilePicture(tempPicture);
                    }
                }
            }

            Set<Picture> newPicts = new HashSet<>();
            for(Picture pict : p.getPictures()){
                if(pict != null){
                    if(pict.isCompressed()){
                        Picture tempPicture = new Picture(pict.getId(),pict.getName(),pict.getType(),decompressBytes(pict.getBytes()));
                        tempPicture.setCompressed(false);
                        newPicts.add(tempPicture);
                        System.out.println("> Picture compressed and saved saved ");
                    }else
                        newPicts.add(pict);
                }
            }
            p.setPictures(newPicts);
        }

        return feedPosts;
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

    public List<User> getUsers(){
        Set<User> usersWithoutAdmin = new HashSet<>();
        List<User> users = userRepository.findAll();
        System.out.println("++++++++++++++++++++");
        for (User u: users){

            if(!u.getName().equals("admin")){
                System.out.println(u.getName());
                usersWithoutAdmin.add(u);
            }
        }
        System.out.println("++++++++++++++++++++");
        return new ArrayList<>(usersWithoutAdmin);
    }

    public Integer hasApplied(User u,Job j){
        for(Job jj: u.getJobApplied()){
            if(jj.getId()==j.getId())
                return 1;
        }

        for(Job jj: u.getJobsCreated()){
            if(jj.getId()==j.getId())
                return 0;
        }

        return -1;
    }

    public boolean hasLiked(User u,Post p){
        for(InterestReaction i: u.getInterestReactions()){
            if(i.getPost()==p)
                return true;
        }
        return false;
    }

    public Integer numOfComments(User u,Post p){
        Integer numOfComments = 0;
        for(Comment c: u.getComments()){
            if(c.getUserMadeBy()==u)
                numOfComments++;
        }
        return numOfComments;
    }

    public Integer matchingSkills(User u,Job j){

        Set<SkillsAndExperience> skills =  u.getSkills();
        Integer avgDistance = 0;
        for(SkillsAndExperience s:skills){
            Integer editDist = Utils.minDistance(s.getDescription().toLowerCase(),j.getTitle().toLowerCase());

            System.out.println("User: "+u.getName());
            System.out.println("Job: "+j.getTitle());
            System.out.println("Skills: "+s.getDescription());
            System.out.println("Score: "+editDist+"\n\n");
            avgDistance += editDist;
        }
        if(u.getCurrentJob()!=null)
            avgDistance += Utils.minDistance(u.getCurrentJob().toLowerCase(),j.getTitle().toLowerCase());
        if (avgDistance != 0) {
            System.out.println("Avg Score: " + (int) (((double) avgDistance) / ((double) skills.size())));
            return (int) (((double) avgDistance) / ((double) skills.size()));
        } else {
            return -1;
        }
    }

    public Integer matchingSkills(User u,Post p) {

        Set<SkillsAndExperience> skills =  u.getSkills();
        Integer avgDistance = 0;
        for(SkillsAndExperience s:skills){
            Integer editDist = Utils.minDistance(s.getDescription().toLowerCase(),p.getContent().toLowerCase());

            System.out.println("User: "+u.getName());
            System.out.println("Job: "+p.getContent());
            System.out.println("Skills: "+s.getDescription());
            System.out.println("Score: "+editDist+"\n\n");
            avgDistance += editDist;
        }

        if(u.getCurrentJob()!=null)
            avgDistance += Utils.minDistance(u.getCurrentJob().toLowerCase(),p.getContent().toLowerCase());

        if (avgDistance != 0) {
            System.out.println("Avg Score: " + (int) (((double) avgDistance) / ((double) skills.size())));
            return (int) (((double) avgDistance) / ((double) skills.size()));
        } else {
            return -1;
        }
    }

    public boolean isNetworkPost(User u,Post p){
        for(Post pp: getFeedPosts(u)){
            if(pp==p)
                return true;
        }
        return false;
    }
}

