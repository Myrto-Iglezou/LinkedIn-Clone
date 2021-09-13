package com.linkedin.linkedinclone.controllers;


import com.intellij.util.indexing.ID;
import com.linkedin.linkedinclone.dto.FeedDTO;
import com.linkedin.linkedinclone.exceptions.PostNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.recommendation.RecommendationAlgos;
import com.linkedin.linkedinclone.repositories.*;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.linkedin.linkedinclone.enumerations.NotificationType.INTEREST;
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
    private final NotificationRepository notificationRepository;
    private final InterestReactionRepository interestReactionRepository;


    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/feed-posts")
    public Set<Post> getFeedPosts(@PathVariable Long id) {
        System.out.println("\n\n\n ------------------- Get feed posts");
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

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
                    Picture dbPic = pictureRepository.findById(pic.getId()).orElseThrow(()->new UserNotFoundException("Pic with "+pic.getId()+" not found"));;
                    Picture tempPicture = new Picture(dbPic.getId(),dbPic.getName(),dbPic.getType(),decompressBytes(dbPic.getBytes()));
                    tempPicture.setCompressed(false);
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
                        tempPicture.setCompressed(false);
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
    @PostMapping(value="/in/{id}/feed/new-post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity newPost(@PathVariable Long id,@RequestPart("object") Post post,@RequestPart(value = "imageFile", required=false) MultipartFile[] files) throws IOException {

        System.out.println("\n\n\n> newPost: "+id);
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        System.out.println("\n> findById\n");
        post.setOwner(currentUser);
        System.out.println("\n> setOwner\n");
        if(files!=null){
            for(MultipartFile file: files){
                Picture pic = new Picture(file.getOriginalFilename() ,file.getContentType() ,compressBytes(file.getBytes()));
                pic.setCompressed(true);
                pic.setPost(post);
                pictureRepository.save(pic);
                System.out.println("> Picture compressed and saved saved ");
            }
        }
        System.out.println("\n> to save\n");
        postRepository.save(post);
        System.out.println("\n\n\n> New post made with success");
        System.out.println(post);
        System.out.println(post.getOwner());
        return ResponseEntity.ok("\"Post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/feed/post-interested/{postdId}")
    public ResponseEntity newInterestedPost(@PathVariable Long id,@PathVariable Long postdId) {

        System.out.println("> /in/{id}/feed/post-interested/{postdId}");
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Post post = postRepository.findById(postdId).orElseThrow(()->new PostNotFoundException("Post with "+id+" not found"));

        // IF reaction exists delete it
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

        InterestReaction newReaction = new InterestReaction(currentUser,post);
        User postOwner = post.getOwner();
        if(postOwner!=currentUser){
            Notification notification = new Notification(INTEREST,postOwner,newReaction);
            notificationRepository.save(notification);
        }
        interestReactionRepository.save(newReaction);
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

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/recommended-posts")
    public List<Post> getRecommendedPosts(@PathVariable Long id) {
        RecommendationAlgos recAlgos = new RecommendationAlgos();
        recAlgos.recommendedPosts(userRepository, postRepository, userService);
        List<Post> recommendedPosts = new ArrayList<>();
        User currentUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with " + id + " not found"));

        System.out.println("\n\n\n 1 ===================================\n\n\n");
        if (currentUser.getRecommendedPosts().size() != 0) {

            for (Post p : currentUser.getRecommendedPosts()) {
                for(Post fp: getFeedPosts(id)){
                    Long id1 = fp.getId();
                    Long id2 = p.getId();
                    if (id1 == id2)
                        recommendedPosts.add(p);
                }
            }
        }else {
            return new ArrayList<>(getFeedPosts(id));
        }
        System.out.println("\n\n\n 2 ===================================\n\n\n");
        Collections.reverse(recommendedPosts);

        System.out.println("\n\n\n 3 ===================================\n\n\n");
        for(Post p: recommendedPosts) {
            User owner = p.getOwner();

            Picture pic = owner.getProfilePicture();
            if(pic != null){
                if(pic.isCompressed()){
                    Picture dbPic = pictureRepository.findById(pic.getId()).orElseThrow(()->new UserNotFoundException("Pic with "+pic.getId()+" not found"));;
                    Picture tempPicture = new Picture(dbPic.getId(),dbPic.getName(),dbPic.getType(),decompressBytes(dbPic.getBytes()));
                    tempPicture.setCompressed(false);
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
                        tempPicture.setCompressed(false);
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
        return recommendedPosts;
    }

}
