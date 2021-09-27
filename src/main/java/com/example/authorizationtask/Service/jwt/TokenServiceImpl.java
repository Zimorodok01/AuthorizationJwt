package com.example.authorizationtask.Service.jwt;

import com.example.authorizationtask.Entity.User;
import com.example.authorizationtask.Security.jwt.JwtConfig;
import com.example.authorizationtask.Service.jwt.TokenService;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    @Override
    public String getRefreshToken(String subject, Collection<? extends GrantedAuthority> authorities, Integer days) {
        return "Bearer " + Jwts.builder()
                .setSubject(subject)
                .claim("authorities", authorities)
                .claim("token","REFRESH")
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()
                        .plusDays(days)))
                .signWith(secretKey)
                .compact();
    }



    @Override
    public String getAccessToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,1);
        return "Bearer " + Jwts.builder()
                .setSubject(subject)
                .claim("authorities", authorities)
                .claim("token","ACCESS")
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String readAccessToken(String token) {
        if (Strings.isNullOrEmpty(token) ||
                !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(BAD_REQUEST,"Token is invalid");
        }

        token = token.replace("Bearer ","");

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);

            Claims body = jws.getBody();

            String subject = body.getSubject();

            String tokenType = (String) body.get("token");

            if (!tokenType.equals("ACCESS")) {
                throw new ResponseStatusException(BAD_REQUEST,"Token is not for access");
            }

            return subject;
        } catch (JwtException e) {
            throw new ResponseStatusException(BAD_REQUEST,String.format("Token %s cannot be trusted",token));
        }
    }

    @Override
    public Map<String, String> refresh(String token) {
        if (Strings.isNullOrEmpty(token) ||
                !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(BAD_REQUEST,"Token is invalid");
        }

        token = token.replace("Bearer ","");

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);

            Claims body = jws.getBody();

            String subject = body.getSubject();

            List<Map<String, String>> authorities =
                    (List<Map<String,String>> ) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            String tokenType = (String) body.get("token");

            if (!tokenType.equals("REFRESH")) {
                throw new ResponseStatusException(BAD_REQUEST,"Token is not refresh token");
            }

            LinkedHashMap<String,String> tokens = new LinkedHashMap<>();
            tokens.put("Access token",getAccessToken(subject,simpleGrantedAuthorities));
            tokens.put("Refresh token",getRefreshToken(subject,simpleGrantedAuthorities,jwtConfig.getTokenExpirationAfterDays()));
            return tokens;
        } catch (JwtException e) {
            throw new ResponseStatusException(BAD_REQUEST,String.format("Token %s cannot be trusted",token));
        }
    }
}
