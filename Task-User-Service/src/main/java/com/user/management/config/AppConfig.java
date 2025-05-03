package com.user.management.config;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    // Configuring the security filter chain
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Initializing SecurityFilterChain...");

        http.sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session management
            .authorizeHttpRequests(Authorize -> Authorize
                .requestMatchers("/api/**").authenticated() // All /api/** endpoints require authentication
                .anyRequest().permitAll() // Any other request is allowed without authentication
            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) // Custom JWT filter
            .csrf(csrf -> csrf.disable()) // Disabling CSRF (as it's stateless)
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Enabling CORS

        log.info("SecurityFilterChain successfully configured.");
        return http.build();
    }

    // Configuring CORS (Cross-Origin Resource Sharing) settings
    private CorsConfigurationSource corsConfigurationSource() {
        log.info("Setting up CORS configuration...");
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000",  // Frontend URL for development
                    "https://zosh-food.vercel.app",  // Production frontend URL
                    "http://localhost:4200"  // Another frontend URL for development
                ));
                cfg.setAllowedMethods(Collections.singletonList("*")); // Allow all HTTP methods
                cfg.setAllowCredentials(true); // Allow credentials (cookies, etc.)
                cfg.setAllowedHeaders(Collections.singletonList("*")); // Allow all headers
                cfg.setExposedHeaders(Arrays.asList("Authorization")); // Expose Authorization header for JWT
                cfg.setMaxAge(3600L); // Cache CORS pre-flight requests for 1 hour
                log.info("CORS configuration applied for request from: {}", request.getRemoteHost());
                return cfg;
            }
        };
    }

    // Password encoder bean (using BCrypt hashing)
    @Bean
    PasswordEncoder passwordEncoder() {
        log.info("Initializing BCryptPasswordEncoder...");
        return new BCryptPasswordEncoder();
    }
}
