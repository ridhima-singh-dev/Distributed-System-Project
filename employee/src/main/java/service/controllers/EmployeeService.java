package service.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import service.notification.models.MessageSender;
import service.notification.models.Notification;
import service.notification.models.Receiver;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageSender messageSender;
    
    @Autowired
    public EmployeeService(RabbitTemplate rabbitTemplate, Receiver receiver, MessageSender messageSender) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageSender = messageSender;
    }

    public ResponseEntity<?> sendJobApplication(String moduleName, List<String> info) {
        try {
            
            Notification notification = buildNotification(info);

            String exchangeName = moduleName + "Exchange";
            String routingKey = moduleName.toLowerCase() + "JobQueue";

            rabbitTemplate.convertAndSend(exchangeName, routingKey, notification);

            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("success", true));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success", false));
        }
    }
    

    
    private Notification buildNotification(List<String> info) {
		Notification notification = new Notification();
		notification.setJobId(info.get(0));
		notification.setEmail(info.get(1));
		notification.setCompanyName(info.get(3));
		return notification;
	}
}

