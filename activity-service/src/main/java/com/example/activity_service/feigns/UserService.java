package com.example.activity_service.feigns;

import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service")
public interface UserService {

    @GetMapping("/api/users/{userId}")
    JSONObject getUserData(@PathVariable String userId);
}
