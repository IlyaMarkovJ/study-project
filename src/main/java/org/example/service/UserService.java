package org.example.service;

import org.example.entity.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    Map<Long, User> userStorage = new HashMap<>();
}
