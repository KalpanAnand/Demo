package com.example.kalps.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import com.example.kalps.Exception.ResourceNotFoundException;
import com.example.kalps.entity.userentity;
import com.example.kalps.repository.userRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private userRepository userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users
    @GetMapping
    public List<userentity> getUsers() {
        return userRepo.findAll();
    }

    // Create new user
    @PostMapping
    public userentity createUser(@RequestBody userentity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    	return userRepo.save(user);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public userentity getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User Not Found with this Id " + id));
    }

    // Update user
    @PutMapping("/{id}")
    public userentity updateUser(@RequestBody userentity user, @PathVariable Long id) {
        userentity existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this Id " + id));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        return userRepo.save(existingUser);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userentity existingUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found with this Id " + id));

        userRepo.delete(existingUser);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully with Id " + id);
        return ResponseEntity.ok(response);
    }
}
