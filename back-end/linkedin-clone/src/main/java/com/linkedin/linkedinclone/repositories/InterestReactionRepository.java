package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.InterestReaction;
import com.linkedin.linkedinclone.model.Notification;
import com.linkedin.linkedinclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface InterestReactionRepository extends JpaRepository<InterestReaction, Long>  {

    @Query("SELECT ir FROM InterestReaction ir WHERE ir.post.id  = :postId AND ir.userMadeBy.id = :userId ")
    Optional<InterestReaction> isInterested(@PathVariable Long userId,@PathVariable Long postId);

}
