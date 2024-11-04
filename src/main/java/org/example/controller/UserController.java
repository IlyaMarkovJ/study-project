package org.example.controller;

import org.example.controller.dto.UserCreateRequest;
import org.example.controller.dto.UserResponse;
import org.example.controller.dto.UserUpdateRequest;
import org.example.service.TransferService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    TransferService transferService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public UserResponse getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest user) {
        return userService.createUser(user);
    }

    @PostMapping("{id}/up-balance/{amount}")
    public UserResponse updateBalance(@PathVariable("id") String id, @PathVariable("amount") double amount) {
        return userService.updateBalance(id, amount);
    }

    @PostMapping("transfer/{from}/{to}/{amount}")
    public ResponseEntity<UserResponse> transfer(@PathVariable("from") String from,
                                                 @PathVariable("to") String to,
                                                 @PathVariable("amount") double amount) {
        try {
            transferService.transfer(from, to, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
