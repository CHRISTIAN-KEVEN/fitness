package com.fitness.ai_service.services;

import com.fitness.ai_service.models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListener {

//    @Value("{rabbitmq.}")
//    private String queueName;
    @RabbitListener(queues = "activity.queue")
    public void processActivityMessage(Activity activity) {
        log.info("Message received {}", activity.toString());

    }
}
