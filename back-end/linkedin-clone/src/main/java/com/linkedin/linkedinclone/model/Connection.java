package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAccepted=false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userFollowing;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userFollowed;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"connection_request"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Notification notification;

    public Connection(User userFollowing, User userFollowed) {
        this.userFollowing = userFollowing;
        this.userFollowed = userFollowed;
    }
}
