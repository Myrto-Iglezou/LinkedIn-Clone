package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.InterestReaction;
import com.linkedin.linkedinclone.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Job, Long> {
}
