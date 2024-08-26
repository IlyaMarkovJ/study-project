package org.example.service;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.example.converter.UserConverter.convertUser;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntities -> convertUser(userEntities))
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return convertUser(userEntity);
    }

    public long userCounter() {
        return userRepository.count();
    }

    public UserResponse createUser(UserCreateRequest user) {
        User userEntity = new User();

        userEntity.id = UUID.randomUUID().toString();

        userEntity.login = user.login;
        userEntity.name = user.name;
        userEntity.lastName = user.lastName;
        userEntity.amount = user.amount;

        userEntity.creationDate = LocalDateTime.now();

        userRepository.save(userEntity);

        return convertUser(userEntity);
    }

    public UserResponse updateBalance(String id, double amount) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userEntity.amount = userEntity.amount + amount;

        userRepository.save(userEntity);

        return convertUser(userEntity);
    }

    public UserResponse updateUser(String id, UserUpdateRequest user) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userEntity.name = user.name;
        userEntity.lastName = user.lastName;

        userRepository.save(userEntity);

        return convertUser(userEntity);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
