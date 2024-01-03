package service.controllers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import service.core.models.Job;
import service.core.repositories.JobRepository;
import service.notification.models.Notification;

@RestController
public class AzJobPostController {
    private final JobRepository jobRepository;
    private final RabbitTemplate rabbitTemplate;
    
    public AzJobPostController(JobRepository jobRepository, RabbitTemplate rabbitTemplate) {
        this.jobRepository = jobRepository;
		this.rabbitTemplate = new RabbitTemplate();
    }
    @PostMapping(value="/postJob", consumes="application/json")
    public ResponseEntity<?> createJob(
            @RequestBody Job info) {
        try {
            jobRepository.save(info);
            if (info != null ) { 
				 // Notification notification = buildNotification(info);
				  String jsonMessage = new ObjectMapper().writeValueAsString(info);
				  rabbitTemplate.convertAndSend("amazonJobExchangepost", "amazonJobQueuepost", jsonMessage);
			      System.out.println("Job application posted by Amazon");
			  }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Collections.singletonMap("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("success", false));
        }
    }
    
    @RabbitListener(queues = "amazonJobQueue")
	public void receiveJobApplication(Message message) {
	    Object content = message.getBody();
	    
	    if (content instanceof byte[]) {
	        // Assuming it's JSON content
	        String jsonString = new String((byte[]) content);
	        try {
	            Notification notification = convertJsonToNotification(jsonString);
	            System.out.println("Received job application in Amazon module: " + notification);
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately
	        }
	    } else if (content instanceof String) {
	        // Assuming it's plain text
	        String plainText = (String) content;
	        System.out.println("Received plain text message in Amazon module: " + plainText);
	        // Handle the plain text as needed
	    }
	}
    
    private Notification convertJsonToNotification(String jsonString) throws IOException {
	    return new ObjectMapper().readValue(jsonString, Notification.class);
	}
   
}