package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.InterestReaction;
import com.linkedin.linkedinclone.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestReactionRepository extends JpaRepository<InterestReaction, Long>  {
}
