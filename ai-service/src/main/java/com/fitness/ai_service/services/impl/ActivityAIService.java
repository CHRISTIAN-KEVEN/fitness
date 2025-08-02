package com.fitness.ai_service.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.ai_service.models.Activity;
import com.fitness.ai_service.models.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAIService {

    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = getPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        return processAiResponse(activity, aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates").get(0)
                    .path("contents")
                    .path("parts").get(0)
                    .path("text");

            String text = textNode.asText().replaceAll("```json\n", "")
                    .replaceAll("\n```", "");

//            log.info("PARSED AI Response: " + text);
            JsonNode analysisJson = mapper.readTree(text);
            JsonNode analysisNode = analysisJson.path("analysis");
            StringBuilder sb = new StringBuilder();
            addAnalysisSection(analysisNode, sb, "overall", "Overall :");
            addAnalysisSection(analysisNode, sb, "pace", "Pace :");
            addAnalysisSection(analysisNode, sb, "heartBeat", "Heart Beat :");
            addAnalysisSection(analysisNode, sb, "caloriesBurned", "Calories :");

            List<String> improvements = extractImprovements(analysisJson.path("improvements"));
            List<String> suggestions = extractSuggestions(analysisJson.path("suggestions"));
            List<String> safety = extractSafetyGuidelines(analysisJson.path("safety"));

            return Recommendation.builder()
                    .userId(activity.getUserId())
                    .activityId(activity.getId())
                    .activityType(activity.getType())
                    .recommendation(sb.toString())
                    .improvements(improvements)
                    .suggestions(suggestions)
                    .safety(safety)
                    .createdAt(LocalDateTime.now())
                    .build();

        } catch (Exception ex) {
            log.error("FAILED TO PROCESS AI RESPONSE ",ex);
            return createDefaultRecommendation(activity);
        }
    }

    private Recommendation createDefaultRecommendation(Activity activity) {
        return Recommendation.builder()
                .userId(activity.getUserId())
                .activityId(activity.getId())
                .activityType(activity.getType())
                .recommendation("Warm before you start exercising")
                .improvements(Collections.singletonList("Improve"))
                .suggestions(Collections.singletonList(""))
                .safety(Collections.singletonList("safety"))
                .createdAt(LocalDateTime.now())
                .build();

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

    private void addAnalysisSection(JsonNode analysisNode, StringBuilder stringBuilder, String key, String prefix) {
        if(!analysisNode.path(key).isMissingNode()) {
            stringBuilder.append(prefix);
            stringBuilder.append((analysisNode.path(key).asText()))
                    .append("\n\n");
        }
    }

    private List<String> extractImprovements(JsonNode improvementsNode) {
        List<String> improvements = new ArrayList<>();
        if(improvementsNode.isArray()) {
            improvementsNode.forEach(improvement -> {
                String area = improvement.path("area").asText();
                String detail = improvement.path("recommendation").asText();
                improvements.add(String.format("%s: %s", area, detail));
            });

            if(!improvements.isEmpty()) {
                return improvements;
            }
        }
        return Collections.singletonList("No Specific improvement provided");

    }

    private List<String> extractSuggestions(JsonNode suggestionsNode) {
        List<String> suggestions = new ArrayList<>();
        if(suggestionsNode.isArray()) {
            suggestionsNode.forEach(improvement -> {
                String workout = improvement.path("workout").asText();
                String description = improvement.path("description").asText();
                suggestions.add(String.format("%s: %s", workout, description));
            });

            if(!suggestions.isEmpty()) {
                return suggestions;
            }
        }
        return Collections.singletonList("No Specific suggestions provided");

    }

    private List<String> extractSafetyGuidelines(JsonNode safetyNode) {

        List<String> safety = new ArrayList<>();
        if(safetyNode.isArray()) {
            safetyNode.forEach(item -> {
                safety.add(item.asText());
            });

            if(!safety.isEmpty()) {
                return safety;
            }
        }
        return Collections.singletonList("No Specific safety guidelines provided");
    }

}
