package com.example.activity_service.controllers;

import com.example.activity_service.dtos.requests.ActivityRequest;
import com.example.activity_service.models.Activity;
import com.example.activity_service.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/activities")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> trackActivity(@RequestBody ActivityRequest activityReq) {
        return ResponseEntity.ok(activityService.trackActivity(activityReq));
    }
    @GetMapping
    public ResponseEntity<?> getUserActivities(@RequestParam("start") Optional<Integer> start,
                                           @RequestParam("size") Optional<Integer> size,
                                               @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(activityService.getUserActivities(userId, start, size));
    }
}
