package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Comment;
import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.CommentRepository;
import com.linkedin.linkedinclone.repositories.PostRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void newConnection(User user,Long userFollowingId) {
        User userToBeFollowed = userRepository.findById(userFollowingId).orElseThrow(()-> new UserNotFoundException("Id: "+newConnectionId));

        Connection newConnection = new Connection(user,userToBeFollowed);

        Set<Connection> connectedUsers = user.getUsersFollowing();
        System.out.println(connectedUsers);

        connectedUsers.add(newConnection);
        System.out.println(connectedUsers);
        user.setUsersConnectedWith(connectedUsers);

        userRepository.save(user);
        System.out.println("---------------");
        connectedUsers = newConnection.getUsersConnectedWith();
        System.out.println(connectedUsers==null);
        connectedUsers.add(user);
        System.out.println(connectedUsers==null);



        System.out.println("---------------");
    }


    public void newPost(User user, Post newPost) {
        Set<Post> userPosts = user.getPosts();
        userPosts.add(newPost);
        user.setPosts(userPosts);
        userRepository.save(user);
        newPost.setOwner(user);
        postRepository.save(newPost);
    }

    public void newPostInterested(User user, Post post) {
        Set<Post> userPosts = user.getPostsInterested();
        userPosts.add(post);
        user.setPosts(userPosts);
        userRepository.save(user);

        Set<User> usersInterested = post.getUsersInterested();
        usersInterested.add(user);
        post.setUsersInterested(usersInterested);
        postRepository.save(post);
    }

    public void newPostComment(User user, Post post, Comment comment) {

        Set<Comment> postComments = post.getComments();
        postComments.add(comment);
        post.setComments(postComments);
        postRepository.save(post);

        comment.setUserMadeBy(user);
        comment.setPost(post);
        commentRepository.save(comment);

        Set<Comment> comments = user.getComments();
        comments.add(comment);
        user.setComments(comments);
        userRepository.save(user);
    }
}
