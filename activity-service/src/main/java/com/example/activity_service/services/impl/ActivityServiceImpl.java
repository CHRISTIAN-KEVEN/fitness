package com.example.activity_service.services.impl;

import com.example.activity_service.dtos.requests.ActivityRequest;
import com.example.activity_service.dtos.responses.ActivityResponse;
import com.example.activity_service.models.Activity;
import com.example.activity_service.repositories.ActivityRepository;
import com.example.activity_service.services.ActivityService;
import com.example.activity_service.utils.Constants;
import com.example.activity_service.utils.ResponseBuilder;
import com.example.activity_service.utils.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        // Validate user existence

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

    @Override
    public ResponseBuilder<?> getUserActivities(String userId, Optional<Integer> start, Optional<Integer> size) {
        LOG.info("RETRIEVING USER {}'s ACTIVITIES", userId);
        int pageStart = start.orElse(Constants.DEFAULT_PAGE_START);
        int pageSize = start.orElse(Constants.DEFAULT_PAGE_SIZE);

       try {
           List<Activity> userActivities = activityRepo.findByUserId(userId);
           List<ActivityResponse> userActivitiesResponse = new ArrayList<>();
           if(userActivities != null) {
               userActivities.forEach(activity -> userActivitiesResponse.add(Utilities.buildActivityResponse(activity)));
           }
           return ResponseBuilder.dataReturn(userActivitiesResponse);
       } catch (Exception ex) {
           LOG.error("FAILED TO GET USER ACTIVITIES ", ex);
           return ResponseBuilder.errorMsgReturn("FAILED TO GET USER ACTIVITIES. please try again later !");
       }
    }
}
