package com.wave.Mirissa.services.jwt;

import com.wave.Mirissa.models.User;
import com.wave.Mirissa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found: " + email);
        }

        // ✅ Normalize role to uppercase without ROLE_ prefix
        String role = user.getRole().name().toUpperCase().trim();

        // ✅ Debug logging
        System.out.println("DEBUG >> DB user=" + user.getEmail() + ", role=" + role);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(role)) // authority = ADMIN
        );
    }
}