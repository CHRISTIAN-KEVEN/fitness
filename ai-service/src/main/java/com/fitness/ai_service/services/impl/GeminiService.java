package com.fitness.ai_service.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@Slf4j
//@ConfigurationProperties(prefix = "my.gemini.api")
public class GeminiService {

    @Value("${my.gemini.api.url}")
    private String geminiApiUrl;
    @Value("${my.gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of("contents", new Object[]{
                Map.of("parts", new Object[]{
                        Map.of("text", question),
                })
        });

        log.info("CALLING GEMINI API: {}", requestBody);
        String resp = webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type", "application/json")
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(requestBody).retrieve()
                .bodyToMono(String.class).block();
        log.info("RESPONSE FROM GEMINI API: {}", resp);
        return resp;
    }
}
