package com.submission.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskTaskSubmissionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTaskSubmissionServiceApplication.class, args);
	}

}
