package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false) @NonNull
    private Timestamp timestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"jobApplied","jobsCreated","comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="chat")
    @JsonIgnoreProperties(value = {"chat","jobApplied","jobsCreated","comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    private Set<Message> messages = new HashSet<>();


}
