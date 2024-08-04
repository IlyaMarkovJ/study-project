package org.example.service;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.example.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    Map<Long, User> userStorage = new HashMap<>();

    public UserResponse getUser(long id) {
        User userEntity = userStorage.get(id);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.amount = userEntity.amount;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public UserResponse createUser(UserCreateRequest user) {
        User userEntity = new User();

        userEntity.id = UUID.randomUUID().getLeastSignificantBits();

        userEntity.login = user.login;
        userEntity.name = user.name;
        userEntity.lastName = user.lastName;
        userEntity.amount = user.amount;

        userEntity.creationDate = LocalDateTime.now();

        userStorage.put(userEntity.id, userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.amount = userEntity.amount;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public UserResponse updateUser(long id, UserUpdateRequest user) {
        User userEntity = userStorage.get(id);

        if (userEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userEntity.name = user.name;
        userEntity.lastName = user.lastName;

        userStorage.put(userEntity.id, userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.amount = userEntity.amount;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public UserResponse updateBalance(long id, double amount) {
        User userEntity = userStorage.get(id);

        double balance = userEntity.getAmount();
        userEntity.setAmount(balance + amount);

        userStorage.put(userEntity.id, userEntity);

        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.amount = userEntity.amount;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }

    public void deleteUser(long id) {
        userStorage.remove(id);
    }
}
