package com.linkedin.linkedinclone.repositories;

import com.linkedin.linkedinclone.model.Connection;
import com.linkedin.linkedinclone.model.Notification;
import com.linkedin.linkedinclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>  {

    @Query("SELECT n FROM Notification n WHERE n.connection_request.id  = :id ")
    Optional<Notification> findByConnectionId(@PathVariable Long id);
}
