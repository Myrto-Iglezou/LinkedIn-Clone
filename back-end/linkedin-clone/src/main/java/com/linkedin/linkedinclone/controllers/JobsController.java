package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.exceptions.ObjectExistsException;
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

import java.util.HashSet;
import java.util.Set;

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
    public ResponseEntity newJob(@PathVariable Long id, @RequestBody Job job) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        job.setUserMadeBy(currentUser);
        jobRepository.save(job);
        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/jobs")
    public Set<Job> getJobs(@PathVariable Long id) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Set<Job> jobs = new HashSet<>();
        Set<User> network = userService.getUserNetwork(currentUser);
        for(User u: network){
            jobs.addAll(u.getJobsCreated());
        }
        return jobs;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/jobs/make-application/{jobId}")
    public ResponseEntity newApplication(@PathVariable Long id, @PathVariable Long jobId) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(()->new UserNotFoundException("Job not found"));
        Set<User> usersApplied = job.getUsersApplied();
        if(!usersApplied.contains(currentUser)){
            usersApplied.add(currentUser);
            job.setUsersApplied(usersApplied);
            jobRepository.save(job);
        }else
            throw new ObjectExistsException("Application has already been made");

        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/jobs/{jobId}/applicants")
    public Set<User> getJobApplicants(@PathVariable Long id, @PathVariable Long jobId) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(()->new UserNotFoundException("Job not found"));
        return job.getUsersApplied();
    }




}
