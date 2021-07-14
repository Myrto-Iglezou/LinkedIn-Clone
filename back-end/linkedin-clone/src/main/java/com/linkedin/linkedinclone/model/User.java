package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.linkedin.linkedinclone.model.Role;

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

    @Column(name = "password", nullable = false) @NonNull
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

    @Column(name = "profession")
    private String profession;

    @Column(name = "company")
    private String company;

    @Column(name = "education")
    private String education;


    // ------------ DATA MEMBERS WITH RELATIONS ------------------ //

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JsonIgnoreProperties("user")
    private Picture profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles;



}
