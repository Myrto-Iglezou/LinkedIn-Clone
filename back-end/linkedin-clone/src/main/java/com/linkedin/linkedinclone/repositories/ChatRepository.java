package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.model.Chat;
import com.linkedin.linkedinclone.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    /*@Query("SELECT c FROM Chat c JOIN User u WHERE u.id = :id AND u.id  ")
    Chat findChatByUsers(@PathVariable Long id, @PathVariable Long otherUserId);
    */
}
