package com.user.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.management.config.JwtProvider;
import com.user.management.exception.UserException;
import com.user.management.model.User;
import com.user.management.repository.UserRepository;
import com.user.management.request.LoginRequest;
import com.user.management.response.AuthResponse;
import com.user.management.service.CustomerUserServiceImplementation;
import com.user.management.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerUserServiceImplementation customUserDetails;
    @Autowired
    private UserService userService;

    // Signup API: Registers a new user and generates a JWT token
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String mobile = user.getMobile();
        String role = user.getRole();

        // Check if the email already exists
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Email is already used with another account");
            authResponse.setStatus(false);

            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }

        // Create and save the new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setMobile(mobile);
        createdUser.setRole(role);
        createdUser.setPassword(passwordEncoder.encode(password)); // Encrypt password

        User savedUser = userRepository.save(createdUser);

        // Authenticate the user and generate the JWT token
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Registration Successful");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED); // 201 Created
    }

    // Signin API: Authenticates the user and generates a JWT token
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Authenticate the user and generate the JWT token
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login Success");
        authResponse.setJwt(token);
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK); // 200 OK
    }

    // Helper method for authenticating user credentials
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Check if password matches
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
