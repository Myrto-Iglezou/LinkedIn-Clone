package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Chat;
import com.linkedin.linkedinclone.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
