package com.example.activity_service.services.impl;

import com.example.activity_service.dtos.requests.ActivityRequest;
import com.example.activity_service.dtos.responses.ActivityResponse;
import com.example.activity_service.feigns.UserService;
import com.example.activity_service.models.Activity;
import com.example.activity_service.repositories.ActivityRepository;
import com.example.activity_service.services.ActivityService;
import com.example.activity_service.utils.Constants;
import com.example.activity_service.utils.ResponseBuilder;
import com.example.activity_service.utils.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.bcel.Const;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static Logger LOG = LogManager.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepo;
    private final UserService userService;

    @Autowired
    private ActivityServiceImpl(ActivityRepository activityRepo,
                                UserService userService) {

        this.activityRepo = activityRepo;
        this.userService = userService;
    }

    @Override
    public ResponseBuilder<?> trackActivity(ActivityRequest activityReq) {

        LOG.info("Beginning adding data for activity tracking...");

        // Validate user existence
        JSONObject userServiceReponse = userService.getUserData(activityReq.getUserId());

        if(!Constants.SUCCESS.equals(userServiceReponse.optString(Constants.erc, "0"))) {
            return ResponseBuilder.errorMsgReturn("Unable to verify user. Please try again later !");
        }
        if(userServiceReponse.getJSONArray("data").isEmpty()) {
            return ResponseBuilder.errorMsgReturn("User with ID " + activityReq.getUserId() + " not found !");
        }
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
