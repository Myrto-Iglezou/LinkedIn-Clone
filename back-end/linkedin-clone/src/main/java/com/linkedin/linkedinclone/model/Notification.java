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

    @Column(name = "isShown")
    private Boolean isShown=false;

    @Column(name = "type", nullable = false) @NonNull
    private NotificationType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"usersFollowing","userFollowedBy","posts","comments","notifications","interestReactions","jobsCreated","interactions","jobApplied","messages","chats"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"notification"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Connection connection_request = null;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"notification"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Comment new_comment = null;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"notification"},allowSetters = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private InterestReaction new_interest = null;

    public Notification(@NonNull NotificationType type, User user, Connection connection_request) {
        this.type = type;
        this.user = user;
        this.connection_request = connection_request;
    }

    public Notification(@NonNull NotificationType type, User user, Comment new_comment) {
        this.type = type;
        this.user = user;
        this.new_comment = new_comment;
    }

    public Notification(@NonNull NotificationType type, User user, InterestReaction new_interest) {
        this.type = type;
        this.user = user;
        this.new_interest = new_interest;
    }

}
