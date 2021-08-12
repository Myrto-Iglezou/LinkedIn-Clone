package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
