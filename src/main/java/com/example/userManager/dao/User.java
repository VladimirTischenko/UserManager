package com.example.userManager.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;
    private String password;
    private boolean active;
    private LocalDateTime created;
    private LocalDateTime updated;
}
