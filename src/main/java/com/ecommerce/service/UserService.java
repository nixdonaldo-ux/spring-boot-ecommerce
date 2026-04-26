package com.ecommerce.service;

import com.ecommerce.dto.UserRegistrationDto;
import com.ecommerce.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User registerUser(UserRegistrationDto registrationDto);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAllUsers();

    User updateUser(Long id, UserRegistrationDto userDto);

    void deleteUser(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User save(User user);
}