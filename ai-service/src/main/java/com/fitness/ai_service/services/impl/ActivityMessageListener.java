package com.fitness.ai_service.services.impl;

import com.fitness.ai_service.models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
//    @Value("{rabbitmq.}")
//    private String queueName;
    @RabbitListener(queues = "activity.queue")
    public void processActivityMessage(Activity activity) {
        log.info("Message received {}", activity.toString());
        String resp = activityAIService.generateRecommendation(activity);
        log.info("Recommendation {}", resp);


    }
}
