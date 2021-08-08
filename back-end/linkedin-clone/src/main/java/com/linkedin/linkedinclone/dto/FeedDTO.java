package com.linkedin.linkedinclone.dto;

import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class FeedDTO {

    private User userDetails;
    private Set<Post> posts;
    private Set<User> connectedUsers;


}
