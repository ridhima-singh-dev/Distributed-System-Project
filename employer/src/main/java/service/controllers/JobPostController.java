package service.controllers;
import java.util.Collections;
import java.util.Optional;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.core.models.Job;
import service.core.models.UserActivity;
import service.core.repositories.JobRepository;
import service.core.repositories.UserRepository;

@RestController
public class JobPostController {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    public JobPostController(JobRepository jobRepository,UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(value="/postJob", consumes="application/json")
    public ResponseEntity<?> createJob(
            @RequestBody Job info) {
        System.out.println(info);
        try {
            Job savedJob = jobRepository.save(info);
            String jobId = savedJob.getJobID();
            String email = info.getEmail();

            Optional<UserActivity> existingUserActivityEmail = userRepository.findByEmail(email);
            System.out.println(existingUserActivityEmail + "Find the email");
            if (existingUserActivityEmail.isPresent()) {
                UserActivity existingUserActivity = existingUserActivityEmail.get();

                // Add jobId to jobsPosted
                existingUserActivity.getJobsPosted().add(jobId);

                // Save the updated UserActivity back to the repository
                userRepository.save(existingUserActivity);

                // You can also return a success response here if needed
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Collections.singletonMap("success", true));
            } else {
                // UserActivity document doesn't exist, create a new one
                UserActivity newUserActivity = new UserActivity(email, null, Collections.singletonList(jobId));
                userRepository.save(newUserActivity);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Collections.singletonMap("success", true));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }
}