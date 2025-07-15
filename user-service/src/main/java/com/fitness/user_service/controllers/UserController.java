package com.fitness.user_service.controllers;

import com.fitness.user_service.dtos.requests.UserRegistrationDTO;
import com.fitness.user_service.services.UserService;
import com.fitness.user_service.utils.ResponseBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private static Logger LOG = LogManager.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDto) {
        return ResponseEntity.ok(userService.register(registrationDto));
    }

    @GetMapping("")
    public ResponseEntity<ResponseBuilder<?>> getUsers(@RequestParam(required = false) Optional<Integer> start,
                                      @RequestParam(required = false) Optional<Integer> size,
                                                       @RequestParam(required = false) Boolean usePageable) {

        if(usePageable) {
            return ResponseEntity.ok(userService.getUsersPageableVersion(start, size));
        }
        return ResponseEntity.ok(userService.getUsers(start, size));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBuilder<?>> register(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
}
