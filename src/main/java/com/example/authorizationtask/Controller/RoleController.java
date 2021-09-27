package com.example.authorizationtask.Controller;

import com.example.authorizationtask.Entity.dto.UserDto;
import com.example.authorizationtask.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
    private final UserService userService;

    @Autowired
    public RoleController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public List<UserDto> getAdmins(@RequestHeader("Authorization") String token) {
        return userService.getAdminRoles(token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    public List<UserDto> getUsers(@RequestHeader("Authorization") String token) {
        return userService.getUsersRole(token);
    }

}
