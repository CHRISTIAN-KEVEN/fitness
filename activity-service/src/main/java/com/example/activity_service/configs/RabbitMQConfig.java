package com.example.activity_service.configs;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "my.rabbit.mq")
@Data
public class RabbitMQConfig {

    private String queueName;
    private String exchangeName;
    private String routingKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
