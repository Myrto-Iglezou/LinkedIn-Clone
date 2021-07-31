package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "interest_reaction")
public class InterestReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false) @NonNull
    private Timestamp timestamp;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User userMadeBy;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Post post;

    @OneToOne
    private Notification notification;

}
