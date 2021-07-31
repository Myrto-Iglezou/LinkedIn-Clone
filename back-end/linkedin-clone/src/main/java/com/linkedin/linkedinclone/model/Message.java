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
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false) @NonNull
    private Timestamp timestamp;

    @ManyToOne
    @JsonIgnoreProperties("chat")
    private Chat chat;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private User userMadeBy;
}
