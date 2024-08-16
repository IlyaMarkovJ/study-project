package org.example.converter;

import org.example.controller.dto.UserResponse;
import org.example.entity.User;

public class UserConverter {
    public static UserResponse convertUser(User userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.id = userEntity.id;
        userResponse.login = userEntity.login;
        userResponse.name = userEntity.name;
        userResponse.lastName = userEntity.lastName;
        userResponse.amount = userEntity.amount;
        userResponse.creationDate = userEntity.creationDate;

        return userResponse;
    }
}
