package com.linkedin.linkedinclone.dto;

import com.linkedin.linkedinclone.model.Comment;
import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.InterestReaction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class NotificationsDTO {
    private Set<NetworkUserDTO> connectionsPending;
    private Set<InterestReaction> interestReactions;
    private Set<Comment> comments;
}
