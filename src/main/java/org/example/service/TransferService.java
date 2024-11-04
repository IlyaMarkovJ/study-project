package org.example.service;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public void transfer(String from, String to, double amount) {

        try {
            withdraw(from, amount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        deposit(to, amount);
    }

    private void withdraw(String id, double amount) throws Exception {

        User userEntity =  userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (userEntity.amount - amount < 0) throw new Exception();
        userEntity.amount = userEntity.amount - amount;
    }

    private void deposit(String id, double amount) {

        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        userEntity.amount = userEntity.amount + amount;
    }

}
