package com.task.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for default welcome endpoint of the Task Service.
 */
@RestController
@RequestMapping("/tasks")
public class HomeController {

    private final boolean isServiceAvailable = true; // Simulate availability check

    /**
     * Default welcome endpoint to verify that the task service is up.
     *
     * @return A welcome message if service is available, otherwise error message.
     */
    @GetMapping
    public ResponseEntity<String> welcome() {
        if (isServiceAvailable) {
            // 200 OK: Service is running
            return ResponseEntity.ok("✅ Welcome to the Task Service");
        } else {
            // 503 Service Unavailable: Simulated failure
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body("❌ Task Service is currently unavailable. Please try again later.");
        }
    }
}
