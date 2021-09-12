package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "user")
public class User {

    // ------------ SIMPLE MEMBERS ------------------ //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false) @NonNull
    @Email
    private String username;

    @Column(name = "password") @NotBlank
    private String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "name", nullable = false) @NonNull
    private String name;

    @Column(name = "surname", nullable = false) @NonNull
    private String surname;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "currentJob")
    private String currentJob;

    @Column(name = "currentCompany")
    private String currentCompany;

    @Column(name = "github")
    private String github;

    @Column(name = "website")
    private String website;

    @Column(name = "twitter")
    private String twitter;

    // ------------ DATA MEMBERS WITH RELATIONS ------------------ //

    @OneToOne(cascade = CascadeType.ALL )
    @JsonIgnoreProperties("user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Picture profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = new HashSet<>();

    /* ----------- SKILLS/EDUCATION/EXPERIENCE ----------- */

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userEdu")
    @JsonIgnoreProperties("userEdu")
    private Set<SkillsAndExperience> education = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userExp")
    @JsonIgnoreProperties("userExp")
    private Set<SkillsAndExperience> workExperience = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userSk")
    @JsonIgnoreProperties("userSk")
    private Set<SkillsAndExperience> skills = new HashSet<>();

    /* ----------- NETWORK ----------- */

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userFollowing")
    @JsonIgnoreProperties("userFollowing")
    private Set<Connection> usersFollowing = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userFollowed")
    @JsonIgnoreProperties("userFollowed")
    private Set<Connection> userFollowedBy = new HashSet<>();

    /* ----------- FEED ----------- */

    @OneToMany(fetch = FetchType.EAGER, mappedBy="owner")
    @JsonIgnoreProperties("owner")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"recommendedTo","interestReactions","comments","owner"} , allowSetters = true)
    @Fetch(value= FetchMode.SELECT)
    private List<Post> recommendedPosts = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userMadeBy")
    @JsonIgnoreProperties("userMadeBy")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="user")
    @JsonIgnoreProperties("user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("usersInterested")
    @Fetch(value = FetchMode.SELECT)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<InterestReaction> interestReactions = new HashSet<>();

    /* ----------- JOBS ----------- */

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userMadeBy")
    @JsonIgnoreProperties(value = {"usersApplied","userMadeBy","recommendedTo"})
    private Set<Job> jobsCreated = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"usersApplied","userMadeBy","recommendedTo"})
    private Set<Job> jobApplied = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"usersApplied","userMadeBy","recommendedTo"} , allowSetters = true)
    private List<Job> recommendedJobs = new ArrayList<>();

    /* ----------- CHAT ----------- */

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userMadeBy")
    @JsonIgnoreProperties("userMadeBy")
    private Set<Message> messages = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "users")
    @JsonIgnoreProperties("users")
    private Set<Chat> chats = new HashSet<>();


    public User(@NonNull @Email String username, @NotBlank String password, @NonNull String name, @NonNull String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
