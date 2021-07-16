package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isAccepted=false;

    @ManyToOne
    @JsonIgnoreProperties("usersConnections")
    private User userFollowing;

    @ManyToOne
    @JsonIgnoreProperties("usersConnections")
    private User userFollowed;

    @OneToOne
    private Notification notification;

    public Connection(User userFollowing, User userFollowed) {
        this.userFollowing = userFollowing;
        this.userFollowed = userFollowed;
    }
}
