package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
