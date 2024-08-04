package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("{id}")
    public UserResponse gerUser(@PathVariable("id") long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest user) {
        return userService.createUser(user);
    }

    @PutMapping("{id}")
    public UserResponse updateUser(@PathVariable("id") long id, @RequestBody UserUpdateRequest user) {
        return userService.updateUser(id, user);
    }

    @PostMapping("{id}/up-balance/{amount}")
    public UserResponse updateBalance(@PathVariable("id") long id, @PathVariable("amount") double amount) {
        return userService.updateBalance(id, amount);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
    }
}
