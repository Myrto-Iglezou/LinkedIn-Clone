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
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column @NonNull
    private String title;

    @Column @NonNull
    private String description;

    @Column
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"jobsCreated","comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    private User userMadeBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"jobApplied","jobsCreated","comments", "posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<User> usersApplied = new HashSet<>();
}
