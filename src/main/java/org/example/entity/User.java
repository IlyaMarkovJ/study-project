package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity(name = "users")
public class User {
    @Id
    public String id;

    public String login;
    public String name;
    public String lastName;
    public double amount;

    public LocalDateTime creationDate;
}
