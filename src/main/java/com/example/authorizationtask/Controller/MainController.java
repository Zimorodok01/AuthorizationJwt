package com.example.authorizationtask.Controller;

import com.example.authorizationtask.Service.jwt.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/")
public class MainController {
    private final TokenService tokenService;

    @Autowired
    public MainController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/refresh")
    public Map<String,String> refresh(@RequestHeader("Authorization") String token) {
        return tokenService.refresh(token);
    }
}
