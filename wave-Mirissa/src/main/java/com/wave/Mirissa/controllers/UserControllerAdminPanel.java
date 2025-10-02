package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.UpdateUserRoleDTO;
import com.wave.Mirissa.dtos.UserDTOsecond;
import com.wave.Mirissa.exception.UserNotFoundException;
import com.wave.Mirissa.models.Customization;
import com.wave.Mirissa.models.Role;
import com.wave.Mirissa.models.User;
import com.wave.Mirissa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class UserControllerAdminPanel {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    public List<UserDTOsecond> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTOsecond(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRole()
                ))
                .toList();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody UpdateUserRoleDTO newUserRole, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setRole(newUserRole.getRole());
                    System.out.println(newUserRole.getRole());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return  "User with id "+id+" has been deleted successfully!!!.";
    }

    @GetMapping("/total-users")
    public long getTotalUsers() {
        return userRepository.countByRole(Role.USER);
    }



}