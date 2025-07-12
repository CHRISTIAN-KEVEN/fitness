package com.example.activity_service.utils;

import com.example.activity_service.dtos.responses.ActivityResponse;
import com.example.activity_service.models.Activity;

public class Utilities {

    public static ActivityResponse buildActivityResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(activity.getId());
        activityResponse.setType(activity.getType());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setStartTime(activity.getStartTime());
        activityResponse.setAddtionalMetrics(activity.getAddtionalMetrics());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setUserId(activity.getUserId());

        return activityResponse;
    }
}
