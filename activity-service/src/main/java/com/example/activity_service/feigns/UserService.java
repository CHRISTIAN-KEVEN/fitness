package com.example.activity_service.feigns;

import com.example.activity_service.dtos.responses.ProxyResponse;
import com.example.activity_service.dtos.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service")
public interface UserService {

    @GetMapping("/api/users/{userId}")
    ProxyResponse<UserResponse> getUserData(@PathVariable String userId);
}
