package com.example.authorizationtask.Service.jwt;

import com.example.authorizationtask.Entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public interface TokenService {
    String getRefreshToken(String subject, Collection<? extends GrantedAuthority> authorities,
                           Integer days);

    String getAccessToken(String subject, Collection<? extends GrantedAuthority> authorities);

    String readAccessToken(String token);

    Map<String, String> refresh(String token);
}
