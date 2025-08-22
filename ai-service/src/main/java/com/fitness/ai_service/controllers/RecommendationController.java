package com.fitness.ai_service.controllers;

import com.fitness.ai_service.services.RecommendationService;
import com.fitness.ai_service.utils.ResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
@AllArgsConstructor
@Slf4j
public class RecommendationController {

//    private static Logger LOG = LogManager.getLogger(RecommendationController.class);
    private RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<ResponseBuilder<?>> recommendations(){
        return ResponseEntity.ok(recommendationService.getRecommendations());
    }

    @GetMapping
    @RequestMapping("/user/{userId}")
    public ResponseEntity<ResponseBuilder<?>> getUserRecommendations(@PathVariable String userId){
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }

    @GetMapping
    @RequestMapping("/{recommendationId}")
    public ResponseEntity<ResponseBuilder<?>> getRecommendation(@PathVariable String recommendationId){
        return ResponseEntity.ok(recommendationService.getRecommendation(recommendationId));
    }
}
