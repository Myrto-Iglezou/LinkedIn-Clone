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

    @Column(name = "isCompressed") @NonNull
    private boolean isCompressed=false;

    @Column(name = "type") @NonNull
    private String type;

    @Column(name = "name") @NonNull
    private String name;

    @Column(name = "bytes", length = 200000) @NonNull
    private byte[] bytes;

    // ---------- RELATIONS ---------- //

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "profilePicture")
    @JsonIgnoreProperties("profilePicture")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pictures")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Post post;

    // ---------- CONSTRUCTORS ---------- //
    public Picture(Long id, String name, String type, byte[] bytes){
        this.id = id;
        this.name = name;
        this.type = type;
        this.bytes = bytes;
    }

    public Picture(String name, String type, byte[] bytes){
        this.name = name;
        this.type = type;
        this.bytes = bytes;
    }
}
