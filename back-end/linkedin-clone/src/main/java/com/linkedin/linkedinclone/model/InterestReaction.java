package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "interest_reaction")
public class InterestReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userMadeBy;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Post post;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Notification notification;

    public InterestReaction(User userMadeBy, Post post) {
        this.userMadeBy = userMadeBy;
        this.post = post;
    }
}
