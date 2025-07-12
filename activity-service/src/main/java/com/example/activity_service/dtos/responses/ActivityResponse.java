package com.example.activity_service.dtos.responses;

import com.example.activity_service.utils.Constants;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityResponse {

    private String id;
    private String userId;
    private Constants.ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> addtionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
