package service.notification;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import service.core.models.Job;

@Component
public class Runner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Receiver receiver;

  public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
    this.receiver = receiver;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    
    Job job = new Job(
            "1",
            "Software Engineer",
            "ABC Company",
            "Job description",
            80000.0,
            List.of("Java", "Spring"),
            List.of("applicant1", "applicant2")
    );
    System.out.println("Im before msg convertor");
    
    rabbitTemplate.setMessageConverter(new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter());
    
    System.out.println("Im after msg convertor");

    rabbitTemplate.convertAndSend(ApplicationQueue.topicExchangeName, "job.application", job);
    System.out.println("Im after convert and send");

    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    
    System.out.println("Im after receiver getlatch");
    
    }
  

}
