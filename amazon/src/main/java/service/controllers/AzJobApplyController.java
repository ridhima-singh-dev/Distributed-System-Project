package service.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.core.models.Job;
import service.core.repositories.JobRepository;
import java.util.*;
@RestController
public class AzJobApplyController {
    private final JobRepository jobRepository;
    public AzJobApplyController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostMapping(value="/applyJob", consumes="application/json")
    public ResponseEntity<?> applyJob(@RequestBody List<String> info){
        try {
            System.out.println(info);
            updateJob(info);
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

    private void updateJob(List<String> info) {
        String jobID = info.get(0);
        String emailId = info.get(1);
        Optional<Job> foundJob = jobRepository.findById(jobID);
        if (foundJob.isPresent()) {
            Job job = foundJob.get();
            List<String> applicants = job.getApplicants();
            if (job.getApplicants() == null) {
                applicants = new ArrayList<>();
            }
            applicants.add(emailId);
            jobRepository.save(job);
        }
    }
}
