package com.user.management;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class TaskUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskUserServiceApplication.class, args);
	}

}
