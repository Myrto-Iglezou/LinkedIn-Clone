package com.linkedin.linkedinclone.controllers;


import com.linkedin.linkedinclone.dto.FeedDTO;
import com.linkedin.linkedinclone.exceptions.PostNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.CommentRepository;
import com.linkedin.linkedinclone.repositories.PostRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class FeedController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/feed")
    public FeedDTO getFeed(@PathVariable Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());


        Set<Post> feedPosts = new HashSet<>();
        feedPosts.addAll(user.getPosts());

        Set<User> network = new HashSet<>();

        // Posts from users connections
        Set<Connection> connections = currentUser.getUsersFollowing();
        for(Connection con: connections) {
            User userFollowing = con.getUserFollowing();
            network.add(userFollowing);
            feedPosts.addAll(userFollowing.getPosts());

            Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

            for(InterestReaction ir: interestReactions){
                feedPosts.add(ir.getPost());
            }
        }


        FeedDTO feed = new FeedDTO(user,feedPosts,network);

        return feed;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/feed-posts")
    public Set<Post> getFeedPosts(@PathVariable Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());


        Set<Post> feedPosts = new HashSet<>();
        feedPosts.addAll(user.getPosts());

        Set<User> network = new HashSet<>();

        // Posts from users connections
        Set<Connection> connections = currentUser.getUsersFollowing();
        for(Connection con: connections) {
            User userFollowing = con.getUserFollowing();
            network.add(userFollowing);
            feedPosts.addAll(userFollowing.getPosts());

            Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

            for(InterestReaction ir: interestReactions){
                feedPosts.add(ir.getPost());
            }
        }

        return feedPosts;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/feed-network")
    public Set<User> getFeedNetwork(@PathVariable Long id) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());


        Set<Post> feedPosts = new HashSet<>();
        feedPosts.addAll(user.getPosts());

        Set<User> network = new HashSet<>();

        // Posts from users connections
        Set<Connection> connections = currentUser.getUsersFollowing();
        for(Connection con: connections) {
            User userFollowing = con.getUserFollowing();
            network.add(userFollowing);
            feedPosts.addAll(userFollowing.getPosts());

            Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

            for(InterestReaction ir: interestReactions){
                feedPosts.add(ir.getPost());
            }
        }

        return network;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/in/{id}/feed/new-post")
    public ResponseEntity newPost(@PathVariable Long id,@RequestBody Post post) {

        // AUDIO IMAGES AND VIDEO TO BE DONEEE
        System.out.println(post);
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        userService.newPost(currentUser,post);
        return ResponseEntity.ok("\"Post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/feed/post-interested/{postdId}")
    public ResponseEntity newInterestedPost(@PathVariable Long id,@PathVariable Long postdId) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException("Post with "+id+" not found"));
        userService.newPostInterested(currentUser,post);
        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/feed/new-comment/{postdId}")
    public ResponseEntity newComment(@PathVariable Long id,@PathVariable Long postdId,@RequestBody Comment comment) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException("Post with "+id+" not found"));
        userService.newPostComment(currentUser,post,comment);

        return ResponseEntity.ok("\"Comment in post created with success!\"");
    }
}
