package vidasalud.ms_vidasalud_audit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${messaging.queue.audit}")
    private String auditQueueName;

    @Bean
    public Queue auditQueue() {
        return QueueBuilder.durable(auditQueueName)
                .withArgument("x-dead-letter-exchange", "audit.dlx")
                .withArgument("x-dead-letter-routing-key", auditQueueName + ".dlq")
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}