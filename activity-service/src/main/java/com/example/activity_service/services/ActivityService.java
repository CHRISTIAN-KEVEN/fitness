package com.example.activity_service.services;

import com.example.activity_service.dtos.requests.ActivityRequest;
import com.example.activity_service.dtos.responses.ActivityResponse;
import com.example.activity_service.utils.ResponseBuilder;

import java.util.Optional;

public interface ActivityService {

    ResponseBuilder<?> trackActivity(ActivityRequest activityReq);

    ResponseBuilder<?> getUserActivities(String userId, Optional<Integer> start, Optional<Integer> size);
}
