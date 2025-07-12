package com.fitness.user_service.services;

import com.fitness.user_service.dtos.requests.UserRegistrationDTO;
import com.fitness.user_service.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    ResponseBuilder<?> register(UserRegistrationDTO registrationDto);

    ResponseBuilder<?> getUsers(Optional<Integer> start, Optional<Integer> size);
    ResponseBuilder<?> getUsersPageableVersion(Optional<Integer> start, Optional<Integer> size);

    ResponseBuilder<?> getUser(String userId);
}
