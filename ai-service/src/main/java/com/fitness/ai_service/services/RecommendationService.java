package com.fitness.ai_service.services;

import com.fitness.ai_service.utils.ResponseBuilder;

public interface RecommendationService {
    ResponseBuilder<?> getRecommendations();

    ResponseBuilder<?> getUserRecommendations(String userId);

    ResponseBuilder<?> getRecommendation(String recommendationId);
}
