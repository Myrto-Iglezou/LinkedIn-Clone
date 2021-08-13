package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
