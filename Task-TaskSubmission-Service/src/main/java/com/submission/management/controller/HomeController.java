package com.submission.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController provides a simple welcome endpoint 
 * for the Task Submission Service.
 */
@RestController
public class HomeController {

    /**
     * Health check or basic info endpoint for the submission service.
     *
     * @return A welcome message with HTTP status 200 (OK)
     */
    @GetMapping("/submissions")
    public ResponseEntity<String> homeController() {
        try {
            // Successfully returning welcome message
            return new ResponseEntity<>("Welcome to Task Submission Service", HttpStatus.OK);
        } catch (Exception e) {
            // In case of unexpected internal error
            return new ResponseEntity<>("Internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
