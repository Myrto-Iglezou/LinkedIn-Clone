package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>  {
}
