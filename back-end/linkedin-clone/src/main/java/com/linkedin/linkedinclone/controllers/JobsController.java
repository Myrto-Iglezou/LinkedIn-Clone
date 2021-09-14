package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.exceptions.ObjectExistsException;
import com.linkedin.linkedinclone.exceptions.PostNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.recommendation.RecommendationAlgos;
import com.linkedin.linkedinclone.repositories.CommentRepository;
import com.linkedin.linkedinclone.repositories.JobsRepository;
import com.linkedin.linkedinclone.repositories.PostRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;
import static jdk.nashorn.internal.objects.Global.println;

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
        System.out.println(job);
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        job.setUserMadeBy(currentUser);
        jobRepository.save(job);
        return ResponseEntity.ok("\"Job created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/jobs")
    public Set<Job> getJobs(@PathVariable Long id) {

        System.out.println("\n\ngetJobs\n");
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Set<Job> jobs = new HashSet<>();

/*        jobs.addAll(currentUser.getJobsCreated());
        Set<User> network = userService.getUserNetwork(currentUser);
        for(User u: network){
            jobs.addAll(u.getJobsCreated());
        }*/

        Set<Job> allJobs = new HashSet<>(jobRepository.findAll());
        jobs.addAll(allJobs);


        System.out.println("\n");
        for(Job j: jobs) {
            System.out.println(j);

            User owner = j.getUserMadeBy();

            Picture pic = owner.getProfilePicture();
            if(pic != null){
                if(pic.isCompressed()){
                    Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
                    pic.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }

            Set<User> usersApplied = j.getUsersApplied();
            for(User u: usersApplied) {
                Picture cpic = u.getProfilePicture();
                if(cpic != null){
                    if(cpic.isCompressed()){
                        Picture tempPicture = new Picture(cpic.getId(),cpic.getName(),cpic.getType(),decompressBytes(cpic.getBytes()));
                        cpic.setCompressed(false);
                        u.setProfilePicture(tempPicture);
                    }
                }
            }
        }



        return jobs;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/jobs/make-application/{jobId}")
    public ResponseEntity newApplication(@PathVariable Long id, @PathVariable Long jobId) {

        System.out.println("\n\nnewApplication\n");
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(()->new UserNotFoundException("Job not found"));
        Set<User> usersApplied = job.getUsersApplied();
        if(!usersApplied.contains(currentUser)){
            usersApplied.add(currentUser);
            job.setUsersApplied(usersApplied);
            jobRepository.save(job);
        }else
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Application has already been made!\", "
                    );

        return ResponseEntity.ok("\"Interested in post created with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/jobs/{jobId}/applicants")
    public Set<User> getJobApplicants(@PathVariable Long id, @PathVariable Long jobId) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Job job = jobRepository.findById(jobId).orElseThrow(()->new UserNotFoundException("Job not found"));
        return job.getUsersApplied();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/recommended-jobs")
    public List<Job> getRecommendedJobs(@PathVariable Long id){
        RecommendationAlgos recAlgos = new RecommendationAlgos();
        recAlgos.recommendedJobs(userRepository, jobRepository, userService);
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        List<Job> recommendedJobs = new ArrayList<>();
        if (currentUser.getRecommendedJobs().size() != 0){
            System.out.println("list is null");
            recommendedJobs = currentUser.getRecommendedJobs();
        } else {
            return new ArrayList<>(getJobs(id));
        }

        for (Job j:recommendedJobs  ) {
            System.out.println(j);
        }
        System.out.println(recommendedJobs);
        Collections.reverse(recommendedJobs);

        for(Job j: recommendedJobs) {
            System.out.println(j);

            User owner = j.getUserMadeBy();

            Picture pic = owner.getProfilePicture();
            if(pic != null){
                if(pic.isCompressed()){
                    Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
                    tempPicture.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }

            Set<User> usersApplied = j.getUsersApplied();
            for(User u: usersApplied) {
                Picture cpic = u.getProfilePicture();
                if(cpic != null){
                    if(cpic.isCompressed()){
                        Picture tempPicture = new Picture(cpic.getId(),cpic.getName(),cpic.getType(),decompressBytes(cpic.getBytes()));
                        tempPicture.setCompressed(false);
                        u.setProfilePicture(tempPicture);
                    }
                }
            }
        }

        return recommendedJobs;
    }


}
