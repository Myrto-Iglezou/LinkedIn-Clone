package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.exceptions.PostNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Job;
import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.CommentRepository;
import com.linkedin.linkedinclone.repositories.JobsRepository;
import com.linkedin.linkedinclone.repositories.PostRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class JobsController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final JobsRepository jobRepository ;
    private final CommentRepository commentRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/in/{id}/new-job")
    public ResponseEntity newJob(@PathVariable Long id, @PathVariable Job job) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        job.setUserMadeBy(currentUser);
        jobRepository.save(job);
        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }
}
