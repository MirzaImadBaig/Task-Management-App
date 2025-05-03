package com.user.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.response.ApiResponse;

@RestController
public class HomeController {

    // Endpoint for the home route
    @GetMapping
    public ResponseEntity<ApiResponse> homeController() {
        ApiResponse res = new ApiResponse("Welcome To Task Management Microservice Project", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    // Endpoint for the user-specific home route
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> userHomeController() {
        ApiResponse res = new ApiResponse("Welcome To Task Management User Service", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

}
