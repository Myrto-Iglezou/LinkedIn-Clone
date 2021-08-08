package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.linkedin.linkedinclone.enumerations.NotificationType.CONNECTION_REQUEST;
import static com.linkedin.linkedinclone.enumerations.NotificationType.INTEREST;

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
        User userToBeFollowed = userRepository.findById(userFollowingId).orElseThrow(()-> new UserNotFoundException("Id: "+userFollowingId));
        Connection newConnection = new Connection(user,userToBeFollowed);
        Notification notification = new Notification(CONNECTION_REQUEST,userToBeFollowed,newConnection);

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
        Notification notification = new Notification(INTEREST,user,newReaction);

        notificationRepository.save(notification);
        interestReactionRepository.save(newReaction);
    }

    public void newPostComment(User user, Post post, Comment comment) {

    }
}
