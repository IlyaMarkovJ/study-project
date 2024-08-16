package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @GetMapping("counter")
    public int userCounter() {
        return userService.userCounter();
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest user) {
        return userService.createUser(user);
    }

    @PostMapping("{id}/up-balance/{amount}")
    public UserResponse updateBalance(@PathVariable("id") String id, @PathVariable("amount") double amount) {
        return userService.updateBalance(id, amount);
    }

    @PutMapping("{id}")
    public UserResponse updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest user) {
        return userService.updateUser(id, user);
    }


    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}
