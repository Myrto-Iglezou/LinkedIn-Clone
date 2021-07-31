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
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false) @NonNull
    private String content;

    @Column(name = "timestamp", nullable = false) @NonNull
    private Timestamp timestamp;

    @ManyToOne
    @JsonIgnoreProperties("posts")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User owner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="post")
    @JsonIgnoreProperties("post")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("postsInterested")
    private Set<User> usersInterested = new HashSet<>();

}
