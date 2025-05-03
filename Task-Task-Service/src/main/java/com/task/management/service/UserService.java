package com.task.management.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.task.management.dtos.UserDto;

/**
 * Feign client interface for interacting with the USER-SERVICE.
 * Retrieves the authenticated user's profile based on JWT token.
 */
@FeignClient(name = "USER-SERVICE", url = "http://localhost:5001")
public interface UserService {

    /**
     * Calls the /api/users/profile endpoint on USER-SERVICE.
     *
     * @param jwt The Authorization header containing the JWT.
     * @return UserDto containing the authenticated user's information.
     */
    @GetMapping("/api/users/profile")
    UserDto getUserProfileHandler(@RequestHeader("Authorization") String jwt);
}
