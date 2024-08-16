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

    Map<String, User> userStorage = new HashMap<>();

    public List<UserResponse> getAllUsers() {
        return userStorage.values().stream()
                .map(userEntity -> convertUser(userEntity))
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String id) {
        // User userEntity = userStorage.get(id);
        User userEntity = userRepository.findUser(id);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return convertUser(userEntity);
    }

    public int userCounter() {
        return userStorage.size();
    }

    public UserResponse createUser(UserCreateRequest user) {
        User userEntity = new User();

        userEntity.id = UUID.randomUUID().toString();

        userEntity.login = user.login;
        userEntity.name = user.name;
        userEntity.lastName = user.lastName;
        userEntity.amount = user.amount;

        userEntity.creationDate = LocalDateTime.now();

        // userStorage.put(userEntity.id, userEntity);

        userRepository.saveUser(userEntity);

        return convertUser(userEntity);
    }

    public UserResponse updateBalance(String id, double amount) {
        User userEntity = userStorage.get(id);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userEntity.amount = userEntity.amount + amount;

        userStorage.put(userEntity.id, userEntity);

        return convertUser(userEntity);
    }

    public UserResponse updateUser(String id, UserUpdateRequest user) {
        User userEntity = userStorage.get(id);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userEntity.name = user.name;
        userEntity.lastName = user.lastName;

        userStorage.put(userEntity.id, userEntity);

        return convertUser(userEntity);
    }

    public void deleteUser(String id) {
        userStorage.remove(id);
    }
}
