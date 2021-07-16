package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository  extends JpaRepository<Connection, Long> {
}
