package com.user.management.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Extract JWT from the Authorization header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);
        
        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // Remove the "Bearer " prefix from the token
            
            try {
                // Create a SecretKey using the key stored in JwtConstant
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                
                // Parse the JWT and extract the claims
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
                
                // Extract the email and authorities from the claims
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));
                
                // Log the user and authorities for debugging purposes
                logger.info("JWT Token validated for user: {}", email);
                logger.debug("Authorities extracted: {}", authorities);
                
                // Convert authorities from comma-separated string to GrantedAuthority list
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                
                // Create authentication object and set it in the security context
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (Exception e) {
                // Log the error and throw an exception if token validation fails
                logger.error("Token validation failed: {}", e.getMessage());
                throw new BadCredentialsException("Invalid token...");
            }
        }
        
        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }
}
