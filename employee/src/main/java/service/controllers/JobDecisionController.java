package service.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.core.models.Job;
import service.core.models.UserActivity;
import service.core.repositories.JobRepository;
import service.core.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class JobDecisionController {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    public JobDecisionController(JobRepository jobRepository,UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value="/applyJob", produces="application/json")
    public ResponseEntity<?> applyJob(@RequestBody Job info) {
        try {

//
            updateJob(info);
            updateUserActivity(info);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("success", true));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }

    }
    private void updateJob(Job info) {
        Optional<Job> foundJob = jobRepository.findById(info.getJobID());
        if (foundJob.isPresent()) {
            Job job = foundJob.get();
            List<String> applicants = job.getApplicants();
            if (job.getApplicants() == null) {
                job.setApplicants(new ArrayList<>());
            }
            System.out.println(applicants + "Appicants");
            applicants.add(info.getEmail());
            jobRepository.save(job);
        }
    }

    private void updateUserActivity(Job info){
        String email = info.getEmail();
        Optional<UserActivity> existingUserActivityEmail = userRepository.findByEmail(info.getEmail());
        System.out.println(existingUserActivityEmail + "Email Id");
        if (existingUserActivityEmail.isPresent()) {
            System.out.println("Inside the block");
            UserActivity existingUserActivity = existingUserActivityEmail.get();
            System.out.println("Inside the block" + existingUserActivity.getJobsApplied());
            existingUserActivity.getJobsApplied().add(info.getJobID());
            System.out.println("Inside the block" + existingUserActivity);
            userRepository.save(existingUserActivity);
            System.out.println("Document updated successfully!");


        } else {
            System.out.println("Outside the block");
            UserActivity newUserActivity = new UserActivity(email, null, Collections.singletonList(info.getJobID()));
            userRepository.save(newUserActivity);
        }
    }
}
