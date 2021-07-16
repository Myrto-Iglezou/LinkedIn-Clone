package com.linkedin.linkedinclone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false) @NonNull
    private Timestamp timestamp;

    @ManyToOne
    @JsonIgnoreProperties("jobsCreated")
    private User userMadeBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("jobApplied")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Job> usersApplied = new HashSet<>();
}
