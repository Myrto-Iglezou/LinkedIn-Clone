package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.util.HashSet;
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
    //@NonNull
    private String phoneNumber;

    @Column(name = "city")
    //@NonNull
    private String city;


    // ------------ DATA MEMBERS WITH RELATIONS ------------------ //

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private Picture profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy="user")
    @JsonIgnoreProperties("user")
    private Set<SkillsAndExperience> info = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("usersConnectedWith")
    private Set<User> usersConnectedWith = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="owner")
    @JsonIgnoreProperties("owner")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy="userMadeBy")
    @JsonIgnoreProperties("userMadeBy")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("usersInterested")
    private Set<Post> postsInterested = new HashSet<>();


    public User(@NonNull @Email String username, @NotBlank String password, @NonNull String name, @NonNull String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }
}
