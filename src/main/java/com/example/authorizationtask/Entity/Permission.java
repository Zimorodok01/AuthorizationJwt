package com.example.authorizationtask.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
    USER_READ("user:read"),
    USER_ADD("user:add"),
    USER_DELETE("user:delete"),
    USER_CHANGE("user:change"),
    READ_HIMSELF("read_himself");

    private String permission;
}
