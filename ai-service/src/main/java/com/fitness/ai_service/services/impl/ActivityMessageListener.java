package com.fitness.ai_service.services.impl;

import com.fitness.ai_service.models.Activity;
import com.fitness.ai_service.models.Recommendation;
import com.fitness.ai_service.repositories.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    private final RecommendationRepository recommendationRepo;
//    @Value("{rabbitmq.}")
//    private String queueName;
    @RabbitListener(queues = "activity.queue")
    public void processActivityMessage(Activity activity) {
        log.info("Message received {}", activity.toString());
        Recommendation resp = activityAIService.generateRecommendation(activity);
        recommendationRepo.save(resp);
        log.info("Recommendation RESPONSE{}", resp);


    }
}
