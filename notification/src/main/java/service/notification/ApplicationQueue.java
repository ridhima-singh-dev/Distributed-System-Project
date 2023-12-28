package service.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import service.core.models.*;

@SpringBootApplication
//@ComponentScan(basePackages = "service")
public class ApplicationQueue {

	static final String topicExchangeName = "spring-boot-exchange";

	  static final String queueName = "spring-boot";

	  @Bean
	  Queue queue() {
	    return new Queue(queueName, false);
	  }

	  @Bean
	  TopicExchange exchange() {
	    return new TopicExchange(topicExchangeName);
	  }
	  
	 
	  @Bean
	  Binding binding(Queue queue, TopicExchange exchange) {
		  return BindingBuilder.bind(queue).to(exchange).with("job.application.#");
	  }

	  @Bean
	  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }

	  @Bean
	  MessageListenerAdapter listenerAdapter(Receiver receiver) {
	    return new MessageListenerAdapter(receiver, "receiveMessage");
	  }

	public static void main(String[] args) {
		SpringApplication.run(ApplicationQueue.class, args);
	}
}
