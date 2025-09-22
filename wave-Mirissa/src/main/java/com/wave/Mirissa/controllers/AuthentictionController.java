package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.AuthenticationRequest;
import com.wave.Mirissa.dtos.AuthenticationResponse;
import com.wave.Mirissa.models.User;
import com.wave.Mirissa.repositories.UserRepository;
import com.wave.Mirissa.services.jwt.JwtService;
//import com.wave.Mirissa.utils.JwtUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AuthentictionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService; // <-- updated

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/authentication")
    public AuthenticationResponse createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response
    ) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password");
        } catch (DisabledException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created, register user first");
            return null;
        }

        // Load user details
        final User user = userRepository.findByEmail(authenticationRequest.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + authenticationRequest.getEmail());
        }

        // Generate JWT with role
        final String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of()
                ),
                user.getRole().name(),
                1000 * 60 * 60 * 24 // 24 hours
        );

        // Return response
        return new AuthenticationResponse(
                jwt,
                user.getEmail(),
                user.getRole().name(),
                user.getUsername(),
                user.getId()
        );
    }
}