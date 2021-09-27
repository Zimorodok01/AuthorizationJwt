package com.example.authorizationtask.Entity.dto;

import com.example.authorizationtask.Entity.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserDto implements Serializable {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String role;

    public UserDto(String name, String surname, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
