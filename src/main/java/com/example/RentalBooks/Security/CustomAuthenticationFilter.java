package com.example.RentalBooks.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // Check if content type is JSON
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            try {
                // Parse JSON into a Map
                Map<String, String> authRequest = objectMapper.readValue(request.getInputStream(), Map.class);
                String username = authRequest.get("username"); // use "username" key
                String password = authRequest.get("password");

                // Create the authentication token with the credentials
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, token);
                return this.getAuthenticationManager().authenticate(token);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Fallback to default behavior (e.g., form data)
            return super.attemptAuthentication(request, response);
        }
    }
}
