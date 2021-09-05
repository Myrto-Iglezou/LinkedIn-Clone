package com.linkedin.linkedinclone.recommendation;


import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.model.Job;
import com.linkedin.linkedinclone.model.Post;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.JobsRepository;
import com.linkedin.linkedinclone.repositories.PostRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RecommendationAlgos {

    public void recommendedJobs(UserRepository userRepository, JobsRepository jobsRepository, UserService userService) {

        List<User> userList = userRepository.findByRole(RoleType.PROFESSIONAL);
        List<Job> jobList = jobsRepository.findAll();

        for(int u = 0; u < userList.size() ; u++) {
            userList.get(u).setRecommendedJobs(null);
            userRepository.save(userList.get(u));
        }
        for(int d = 0; d < jobList.size() ; d++){
            jobList.get(d).setRecommendedTo(null);
            jobsRepository.save(jobList.get(d));
        }

        userList = userRepository.findByRole(RoleType.PROFESSIONAL);
        jobList = jobsRepository.findAll();

        System.out.println("userList.size = "+userList.size());
        System.out.println("jobList.size = "+jobList.size());

        if(userList.size() > 0 && jobList.size() > 0) {
            double[][] matrix = new double[userList.size()][jobList.size()];

            for (int u = 0; u < userList.size(); u++) {
                int count = 0;
                double val = 0;
                for (int d = 0; d < jobList.size(); d++) {
                    /*
                        Scoring: [-1,0,1] (sparse)
                        --------------------------------
                        - [1] if applied to this job
                        - [0] if it has been posted by him
                        - [-1] if it nothing from the above
                    */
                    matrix[u][d] = userService.hasApplied(userList.get(u),jobList.get(d));
                    if (matrix[u][d] != -1) {
                        val += matrix[u][d];
                        count++;
                    } else
                        matrix[u][d] = -2;
                }

                for(int d = 0 ; d < jobList.size(); d++){
                    if (matrix[u][d] == -2 && count != 0)
                        matrix[u][d] = val / count;
                    else if(count == 0) {
                        matrix[u][d] = -1;
                    }
                }

            }


            Recommendation recommendation = new Recommendation();
            double[][] results = recommendation.matrix_factorization(matrix, 2, 0.0002, 0.0);
            System.out.println("MATRIX:");
            recommendation.print(matrix);
            System.out.println("RESULTS:");
            recommendation.print(results);

            for (int u = 0; u < userList.size(); u++) {
                List<Job> jobs = new ArrayList<>();
                List<Pair> pairs = new ArrayList<>();
                for (int d = 0; d < jobList.size(); d++) {
                    if (matrix[u][d] == -1)
                        pairs.add(new Pair(d, results[u][d]));
                }
                pairs.sort((Pair p1, Pair p2) -> Double.compare(p2.value, p1.value));
                if (pairs.size() > 0) {
                    //int topk = Math.min(pairs.size(), 5);
                    System.out.println("pairs.size = "+pairs.size());
                    for (int i = 0; i < pairs.size(); i++) {
                        jobs.add(jobList.get(pairs.get(i).index));
                    }
                    userList.get(u).setRecommendedJobs(jobs);
                }
                if( pairs.size() == jobList.size())
                    userList.get(u).setRecommendedJobs(null);
            }

            userRepository.saveAll(userList);

        }
        
    }

    public void recommendedPosts(UserRepository userRepository, PostRepository postRepository, UserService userService) {

        List<User> userList = userRepository.findByRole(RoleType.PROFESSIONAL);
        List<Post> postList = postRepository.findAll();

        for(int u = 0; u < userList.size() ; u++) {
            userList.get(u).setRecommendedPosts(null);
            userRepository.save(userList.get(u));
        }

        for(int d = 0; d < postList.size() ; d++){
            postList.get(d).setRecommendedTo(null);
            postRepository.save(postList.get(d));
        }

        userList = userRepository.findByRole(RoleType.PROFESSIONAL);
        postList = postRepository.findAll();

        System.out.println(userList.size());
        System.out.println(postList.size());


        if(userList.size() > 0 && postList.size() > 0) {
            double[][] matrix = new double[userList.size()][postList.size()];

            for (int u = 0; u < userList.size(); u++) {
                int count = 0;
                double val = 0;
                for (int d = 0; d < postList.size(); d++) {

                    /*
                        Scoring between -1 and 5 overall
                        --------------------------------
                        - +3 if interested
                        - +2 if commented
                        * if nothing from above:
                            +1 if this post is in network
                          else -1
                    */
                    matrix[u][d] = 0;
                    if(userService.hasLiked(userList.get(u),postList.get(d)))
                        matrix[u][d] += 3;

                    Integer numOfComments = userService.numOfComments(userList.get(u), postList.get(d));
                    if (numOfComments>0)
                        matrix[u][d] += 2;


                    if (matrix[u][d] != 0) {
                        val += matrix[u][d];
                        count++;
                    }else
                        matrix[u][d] = -2;
                }

                for(int d = 0 ; d < postList.size(); d++){
                    if (matrix[u][d] == -2 && count != 0)
                        matrix[u][d] = val / count;
                    else if(count == 0) {
                        if(userService.isNetworkPost(userList.get(u), postList.get(d)))
                            matrix[u][d] = 1;
                        else
                        matrix[u][d] = -1;
                    }
                }

            }

            Recommendation recommendation = new Recommendation();
            double[][] results = recommendation.matrix_factorization(matrix, 2, 0.0002, 0.0);
            recommendation.print(matrix);
            recommendation.print(results);

            for (int u = 0; u < userList.size(); u++) {
                List<Post> posts = new ArrayList<>();
                List<Pair> pairs = new ArrayList<>();
                for (int d = 0; d < postList.size(); d++) {
                    if (matrix[u][d] == -1)
                        pairs.add(new Pair(d, results[u][d]));
                }
                pairs.sort((Pair p1, Pair p2) -> Double.compare(p2.value, p1.value));
                if (pairs.size() > 0) {
                    //int topk = Math.min(pairs.size(), 5);
                    for (int i = 0; i < pairs.size(); i++)
                        posts.add(postList.get(pairs.get(i).index));
                    userList.get(u).setRecommendedPosts(posts);
                }
                if( pairs.size() == postList.size())
                    userList.get(u).setRecommendedPosts(null);
            }

            userRepository.saveAll(userList);

        }

    }

}

