package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "pictureBytes", nullable = false,length = 200000)
    private byte[] pictureBytes;

    // ---------- RELATIONS ---------- //

    @OneToOne(mappedBy = "profilePicture")
    @JsonIgnoreProperties("profilePicture")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

}
