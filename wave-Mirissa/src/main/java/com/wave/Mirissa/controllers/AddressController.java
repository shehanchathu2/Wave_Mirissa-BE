package com.wave.Mirissa.controllers;

import com.wave.Mirissa.models.Address;
import com.wave.Mirissa.models.User;
import com.wave.Mirissa.repositories.AddressRepository;
import com.wave.Mirissa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/{userId}")
    public Address saveAddress(@PathVariable Long userId, @RequestBody Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        address.setUser(user);

        // If user already has an address → update
        Address existing = addressRepository.findByUserId(userId);
        if (existing != null) {
            address.setId(existing.getId());
        }

        return addressRepository.save(address);
    }

    @GetMapping("/{userId}")
    public Address getUserAddress(@PathVariable Long userId) {
        return addressRepository.findByUserId(userId);
    }
}