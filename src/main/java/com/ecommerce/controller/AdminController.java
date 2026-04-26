package com.ecommerce.controller;

import com.ecommerce.dto.UserRegistrationDto;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserRegistrationDto>> getAllUsers() {
        List<UserRegistrationDto> users = userService.findAllUsers().stream()
                .map(user -> {
                    UserRegistrationDto dto = new UserRegistrationDto();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setPhoneNumber(user.getPhoneNumber());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserRegistrationDto> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    UserRegistrationDto dto = new UserRegistrationDto();
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setPhoneNumber(user.getPhoneNumber());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserRegistrationDto> updateUser(@PathVariable Long id, @RequestBody UserRegistrationDto userDto) {
        UserRegistrationDto updatedUser = new UserRegistrationDto();
        // Implementation would go here
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Additional admin endpoints can be added here
    @GetMapping("/dashboard/stats")
    public ResponseEntity<String> getDashboardStats() {
        // Return dashboard statistics
        return ResponseEntity.ok("Dashboard stats would be implemented here");
    }
}