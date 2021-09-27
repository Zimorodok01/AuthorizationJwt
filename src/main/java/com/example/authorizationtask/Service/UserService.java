package com.example.authorizationtask.Service;

import com.example.authorizationtask.Entity.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(String token);

    UserDto getUserByToken(String token);

    UserDto addUser(String token, UserDto userDto);

    UserDto updateUser(String token, Long id, String name, String surname, String username, String password, String role);

    UserDto deleteUser(String token, Long id);

    List<UserDto> getUsersSortedByName(String token);

    List<UserDto> getUsersSortedBySurname(String token);

    List<UserDto> getUsersSortedByUsername(String token);

    List<UserDto> getAdminRoles(String token);

    List<UserDto> getUsersRole(String token);
}
