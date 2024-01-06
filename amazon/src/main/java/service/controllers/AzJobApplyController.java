package service.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.notification.controller.*;
import service.core.models.Job;
import service.core.repositories.JobRepository;
import service.notification.models.*;

@RestController
public class AzJobApplyController {
    private final JobRepository jobRepository;
    //private final MessageController messageController;
    private final RabbitTemplate rabbitTemplate;

    public AzJobApplyController(JobRepository jobRepository, RabbitTemplate rabbitTemplate) {
        this.jobRepository = jobRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(value = "/applyJob", consumes = "application/json")
    public ResponseEntity<?> applyJob(@RequestBody List<String> info) {
        try {
            System.out.println(info);
            updateJob(info);

            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("success", true));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }

    @RabbitListener(queues = "amazonJobQueue")
    public void receiveJobApplication(Message message) {
        System.out.println("Message: " + message);
        Object content = message.getBody();


        if (content instanceof byte[]) {
            String jsonString = new String((byte[]) content);
            System.out.println("Received job application in Amazon : " + jsonString);
        } else if (content instanceof String) {
            String plainText = (String) content;
            System.out.println("Received plain text message in Amazon : " + plainText);
        }
    }

    private Notification buildNotification(List<String> info) {
        Notification notification = new Notification();
        notification.setJobId(info.get(0));
        notification.setEmail(info.get(1));
        notification.setCompanyName(info.get(3));
        return notification;
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
    private Notification convertJsonToNotification(String jsonString) throws IOException {
        System.out.println("Jsonstring:" + jsonString );
        return new ObjectMapper().readValue(jsonString, Notification.class);
    }

}
