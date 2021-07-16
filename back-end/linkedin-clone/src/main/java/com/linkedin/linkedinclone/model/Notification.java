package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.linkedin.linkedinclone.enumerations.NotificationType;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false) @NonNull
    private NotificationType type;

    @ManyToOne
    @JsonIgnoreProperties("notifications")
    private User user;

    @OneToOne
    private Connection connection_request = null;

    @OneToOne
    private Comment new_comment = null;

    @OneToOne
    private InterestReaction new_interest = null;

}
