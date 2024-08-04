package org.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    public long id;

    public String login;
    public String name;
    public String lastName;
    public double amount;

    public LocalDateTime creationDate;
}
