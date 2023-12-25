package service.controller;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import service.core.models.Job;
import service.core.repositories.JobRepository;

@RestController
public class JobController {
    @Value("${server.port}")
    private int port;
    private final JobRepository jobRepository;
    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @PostMapping(value="/postJob", consumes="application/json")
    public ResponseEntity<?> createJob(
            @RequestBody Job info) {
        System.out.println(info);
        try {
            jobRepository.save(info);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }

    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            return "localhost:" + port;
        }
    }
}