package com.example.authorizationtask.Controller;

import com.example.authorizationtask.Entity.User;
import com.example.authorizationtask.Entity.dto.UserDto;
import com.example.authorizationtask.Service.UserService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserDto> getAllUsers(@RequestHeader("Authorization") String token) {
        return userService.getUsers(token);
    }

    @GetMapping("myProfile")
    public UserDto getMyProfile(@RequestHeader("Authorization") String token) {
        return userService.getUserByToken(token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserDto addNewUser(@RequestHeader("Authorization") String token, @RequestBody UserDto newUser) {
        return userService.addUser(token,newUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public UserDto updateUser(

            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long id,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "surname",required = false) String surname,
            @RequestParam(value = "username",required = false) String username,
            @RequestParam(value = "password",required = false) String password,
            @RequestParam(value = "role",required = false) String role) {
        return userService.updateUser(token,id,name,surname,username,password,role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public UserDto deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") Long id) {
        return userService.deleteUser(token,id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/name")
    public List<UserDto> getUsersSortedByName(@RequestHeader("Authorization") String token) {
        return userService.getUsersSortedByName(token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/surname")
    public List<UserDto> getUsersSortedBySurname(@RequestHeader("Authorization") String token) {
        return userService.getUsersSortedBySurname(token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/username")
    public List<UserDto> getUsersSortedByUsername(@RequestHeader("Authorization") String token) {
        return userService.getUsersSortedByUsername(token);
    }
}
