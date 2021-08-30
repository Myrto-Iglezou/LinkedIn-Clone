package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false) @NonNull
    private String content;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"posts","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User owner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="post", orphanRemoval=true)
    @JsonIgnoreProperties(value = {"post","usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="post", orphanRemoval=true)
    @JsonIgnoreProperties(value = {"post"},allowSetters = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<InterestReaction> interestReactions = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="post")
    @JsonIgnoreProperties(value = {"post","user"},allowSetters = true)
    private Set<Picture> pictures = new HashSet<>();


    public Post(String content) {
        this.content = content;
    }
}
