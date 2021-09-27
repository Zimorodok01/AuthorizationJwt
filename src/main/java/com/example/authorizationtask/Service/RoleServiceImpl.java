package com.example.authorizationtask.Service;

import com.example.authorizationtask.Entity.Role;
import com.example.authorizationtask.Repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final String ROLE_DOES_NOT_EXIST = "Role %s doesn't exist";


    @Override
    public Role getAdmin() {
        return roleRepo.findById(1L).orElseThrow(
                () -> new ResponseStatusException(BAD_REQUEST,
                        "Admin role doesn't exist")
        );
    }

    @Override
    public Role getUser() {
        return roleRepo.findById(2L).orElseThrow(
                () -> new ResponseStatusException(BAD_REQUEST,
                        "User role doesn't exist")
        );
    }

    @Override
    public Role getRole(String role) {
        return roleRepo.findByName(role.toUpperCase()).orElseThrow(
                () -> new ResponseStatusException(BAD_REQUEST,String.format(ROLE_DOES_NOT_EXIST,role))
        );
    }
}
