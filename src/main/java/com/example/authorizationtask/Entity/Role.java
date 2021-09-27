package com.example.authorizationtask.Entity;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.authorizationtask.Entity.Permission.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    private Long id;
    private String name;

    private Set<Permission> getUserPermission() {
        return Sets.newHashSet(
                READ_HIMSELF
        );
    }

    private Set<Permission> getAdminPermission() {
        return Sets.newHashSet(
                USER_READ,
                USER_ADD,
                USER_DELETE,
                USER_CHANGE,
                READ_HIMSELF
        );
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<Permission> permissions;
        switch (name) {
            case "USER":
                permissions = getUserPermission();
                break;
            case "ADMIN":
                permissions = getAdminPermission();
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Role has invalid name");
        }
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name));
        return authorities;
    }
}
