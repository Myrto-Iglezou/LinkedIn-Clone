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

    @Column(name = "format", nullable = false)
    private String format;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "bytes", nullable = false,length = 200000)
    private byte[] bytes;

    // ---------- RELATIONS ---------- //

    @OneToOne(mappedBy = "profilePicture")
    @JsonIgnoreProperties("profilePicture")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;


    // ---------- CONSTRUCTORS ---------- //
    public Picture(String name, String format, byte[] bytes){
        this.name = name;
        this.format = format;
        this.bytes = bytes;
    }
}
