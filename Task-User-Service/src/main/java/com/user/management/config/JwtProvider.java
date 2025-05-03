package com.user.management.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {

    // Secret key used to sign the JWT
    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    
    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);

    // Generate a JWT token for the authenticated user
    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);
        
        log.info("Generating JWT token for user: {}", auth.getName());
        
        // Set token expiration to 24 hours from now
        String jwt = Jwts.builder()
                .setIssuedAt(new Date()) // Issued time of the token
                .setExpiration(new Date(new Date().getTime() + 86400000)) // Expiry time (24 hours)
                .claim("email", auth.getName()) // Add the email claim (username)
                .claim("authorities", roles) // Add roles as authorities
                .signWith(key) // Sign the token with the secret key
                .compact();
        
        log.debug("Generated JWT token: {}", jwt); // Be cautious about logging the full token
        return jwt;
    }

    // Extract email from the JWT token
    public static String getEmailFromJwtToken(String jwt) {
        log.info("Extracting email from JWT token");

        // Remove "Bearer " prefix if it exists
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }
        
        // Parse JWT and get claims
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(jwt)
                            .getBody();
        
        String email = String.valueOf(claims.get("email"));
        log.debug("Extracted email: {}", email);
        return email;
    }

    // Convert authorities collection to a comma-separated string
    public static String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths = new HashSet<>();
        
        for (GrantedAuthority authority : collection) {
            auths.add(authority.getAuthority());
        }
        
        String joined = String.join(",", auths); // Combine authorities into a single string
        log.debug("Populated authorities: {}", joined);
        return joined;
    }
}
