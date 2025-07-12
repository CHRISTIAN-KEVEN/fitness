package com.example.activity_service.services.impl;

import com.example.activity_service.dtos.requests.ActivityRequest;
import com.example.activity_service.dtos.responses.ActivityResponse;
import com.example.activity_service.models.Activity;
import com.example.activity_service.repositories.ActivityRepository;
import com.example.activity_service.services.ActivityService;
import com.example.activity_service.utils.ResponseBuilder;
import com.example.activity_service.utils.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static Logger LOG = LogManager.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepo;

    @Autowired
    private ActivityServiceImpl(ActivityRepository activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public ResponseBuilder<?> trackActivity(ActivityRequest activityReq) {

        LOG.info("Beginning adding data for activity tracking...");
        Activity activity = new Activity();
        activity.setUserId(activityReq.getUserId());
        activity.setDuration(activityReq.getDuration());
        activity.setStartTime(activityReq.getStartTime());
        activity.setType(activityReq.getActivityType());
        activity.setCaloriesBurned(activityReq.getCaloriesBurned());
        activity.setAddtionalMetrics(activityReq.getAdditionMetrics());
        Activity savedActivity = activityRepo.save(activity);

        LOG.info("Successfully added data for activity tracking !");

        return ResponseBuilder.dataReturn(Utilities.buildActivityResponse(savedActivity));
    }
}
