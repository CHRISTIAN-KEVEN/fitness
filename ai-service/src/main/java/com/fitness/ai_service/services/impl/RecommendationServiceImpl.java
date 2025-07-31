package com.fitness.ai_service.services.impl;

import com.fitness.ai_service.models.Recommendation;
import com.fitness.ai_service.repositories.RecommendationRepository;
import com.fitness.ai_service.services.RecommendationService;
import com.fitness.ai_service.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Override
    public ResponseBuilder<?> getRecommendations() {
        return null;
    }

    @Override
    public ResponseBuilder<?> getUserRecommendations(String userId) {
        try {
            List<Recommendation> recommendationList = recommendationRepository.findByUserId(userId);
            log.info("User {} Recommendations {}", userId, recommendationList.toString());
            return ResponseBuilder.dataReturn(recommendationList);
        } catch (Exception e) {
            log.error("Failed to get user Recommendations", e);
            return ResponseBuilder.errorMsgReturn("Failed to get user Recommendations");
        }
    }

    @Override
    public ResponseBuilder<?> getRecommendation(String recommendationId) {
        try {
            Optional<Recommendation> optRecommendation = recommendationRepository.findById(recommendationId);
            log.info("Details for recommendation with ID {} - DETAILS : {}", recommendationId, optRecommendation);
            if(optRecommendation.isPresent()) {
                return ResponseBuilder.dataReturn(optRecommendation.get());
            }
            else {
                return ResponseBuilder.successMsgReturn("No recommendation with ID " + recommendationId);
            }
        } catch (Exception e) {
            log.error("Failed to get user Recommendations", e);
            return ResponseBuilder.errorMsgReturn("Failed to get user Recommendations");
        }
    }
}
