package com.linkedin.linkedinclone.controllers;


import com.intellij.util.indexing.ID;
import com.linkedin.linkedinclone.dto.FeedDTO;
import com.linkedin.linkedinclone.exceptions.PostNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.*;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.linkedin.linkedinclone.utils.PictureSave.compressBytes;
import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@RestController
@AllArgsConstructor
public class FeedController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;
    private final CommentRepository commentRepository;
    private final InterestReactionRepository interestReactionRepository;


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
        System.out.println("\n\n\n ------------------- Get feed posts");
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

        Set<Post> feedPosts = new HashSet<>();
        feedPosts.addAll(user.getPosts());


        // Posts from users connections
        Set<Connection> connections = user.getUsersFollowing();
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

/*        // Posts from users connections
        Set<Connection> connections2 = user.getUserFollowedBy();
        for(Connection con: connections2) {
            if(con.getIsAccepted()){
                User userFollowing = con.getUserFollowing();
                feedPosts.addAll(userFollowing.getPosts());

                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

                for(InterestReaction ir: interestReactions){
                    feedPosts.add(ir.getPost());
                }
            }
        }*/

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
/*            System.out.println(owner);
            System.out.println(p);*/
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
    @PostMapping(value="/in/{id}/feed/new-post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity newPost(@PathVariable Long id,@RequestPart("object") Post post,@RequestPart(value = "imageFile", required=false) MultipartFile[] files) throws IOException {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        post.setOwner(currentUser);
        if(files!=null){
            for(MultipartFile file: files){
                Picture pic = new Picture(file.getOriginalFilename() ,file.getContentType() ,compressBytes(file.getBytes()));
                pic.setCompressed(true);
                pic.setPost(post);
                pictureRepository.save(pic);
                System.out.println("> Picture compressed and saved saved ");
            }
        }
        postRepository.save(post);
        System.out.println("\n\n\n> New post made with success");
        System.out.println(post);
        System.out.println(post.getOwner());
        return ResponseEntity.ok("\"Post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/feed/post-interested/{postdId}")
    public ResponseEntity newInterestedPost(@PathVariable Long id,@PathVariable Long postdId) {

        System.out.println("/in/{id}/feed/post-interested/{postdId}");
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Post post = postRepository.findById(postdId).orElseThrow(()->new PostNotFoundException("Post with "+id+" not found"));

        for(InterestReaction ir: post.getInterestReactions()) {
            if(ir.getUserMadeBy()==currentUser) {

                currentUser.getInterestReactions().remove(ir);
                currentUser.setInterestReactions(currentUser.getInterestReactions());
                post.getInterestReactions().remove(ir);
                post.setInterestReactions(post.getInterestReactions());
                ir.setUserMadeBy(null);
                ir.setPost(null);
                userRepository.save(currentUser);
                postRepository.save(post);
                interestReactionRepository.delete(ir);
                System.out.println("> Reaction deleted");
                System.out.println(post);
                return ResponseEntity.ok("\"Reaction deleted with success!\"");
            }
        }

        userService.newPostInterested(currentUser,post);
        System.out.println("> New reaction made with success");
        System.out.println(post);
        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/feed/is-interested/{postdId}")
    public ResponseEntity<?> isInterestedPost(@PathVariable Long id, @PathVariable Long postdId) {

        System.out.println("\n> ----- Reaction search\n");
        InterestReaction isInterested = null;
        Post post = postRepository.findById(postdId).orElseThrow(()->new PostNotFoundException("Post with "+id+" not found"));
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

        for(InterestReaction ir: post.getInterestReactions()) {
            if(ir.getUserMadeBy()==currentUser) {
                System.out.println("> Reaction found");
                return ResponseEntity.ok(ir);
            }
        }
        System.out.println("> "+isInterested);
        System.out.println(post);
        return ResponseEntity.ok(null);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/feed/new-comment/{postdId}")
    public ResponseEntity newComment(@PathVariable Long id,@PathVariable Long postdId,@RequestBody Comment comment) {

        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Post post = postRepository.findById(postdId).orElseThrow(()->new PostNotFoundException("Post with "+id+" not found"));
        userService.newPostComment(currentUser,post,comment);
        System.out.println("> New comment made with success");
        System.out.println(comment);
        System.out.println(post);
        return ResponseEntity.ok("\"Comment in post created with success!\"");
    }
}
