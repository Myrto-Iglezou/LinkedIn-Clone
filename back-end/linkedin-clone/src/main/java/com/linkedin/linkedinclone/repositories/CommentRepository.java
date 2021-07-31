package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Comment;
import com.linkedin.linkedinclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
