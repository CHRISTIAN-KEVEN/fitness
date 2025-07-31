package com.fitness.ai_service.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.ai_service.models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAIService {

    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = getPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        processAiResponse(activity, aiResponse);
        log.info("AI Response: " + aiResponse);
        return aiResponse;
    }

    private void processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates").get(0)
                    .path("contents")
                    .path("parts").get(0)
                    .path("text");

            String text = textNode.asText().replaceAll("```json\n", "")
                    .replaceAll("\n```", "");

            log.info("PARSED AI Response: " + text);

        } catch (Exception ex) {
            log.error("FAILED TO PROCESS AI RESPONSE ",ex);
        }
    }

    private String getPromptForActivity(Activity activity) {
        return String.format("""
                Analyze this fitness activity and provide detailed recommendation in the format
                {
                    "analysis": {
                        "overall": "Overall analysis here",
                        "pace": "pace analysis here",
                        "heartRate": "heart rate analysis here",
                        "caloriesBurned": "calories burned analysis here"
                     },
                     "improvements": [
                         {
                            "area": "Area name",
                            "recommendation": "Detailed recommendation",
                         }
                      ],
                      "suggestions": [
                            {
                              "workout": "Workout name",
                              "description": "Detailed workout description",
                            }
                       ],
                       "safety": [
                            "Safety point 1",
                            "Safety point 2",
                       ]
                }
                
                Analyze this activity:
                Activity Type %s
                Duration: %d minutes
                Calories Burned: %d
                Additional Metrics: %s
                
                Provide detailed analysis focusing on performance, improvements, next workout suggestions and safety guidelines
                Ensure the response follows the EXACT JSON FORMAT given above
                """, activity.getType(), activity.getDuration(), activity.getCaloriesBurned(), activity.getAddtionalMetrics());
    }
}
