package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.SignupRequest;
import com.wave.Mirissa.dtos.UserDTO;
import com.wave.Mirissa.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class signupUserController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest){
        UserDTO createdUser = authService.createUser(signupRequest);
        if(createdUser == null)
          return new ResponseEntity<>("OOps user not created!!,Try again shortly", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);


    }
}
