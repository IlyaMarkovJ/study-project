package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    public String id;

    public String login;
    public String name;
    public String lastName;
    public double amount;

    public LocalDateTime creationDate;
}
