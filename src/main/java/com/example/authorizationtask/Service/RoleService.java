package com.example.authorizationtask.Service;

import com.example.authorizationtask.Entity.Role;

public interface RoleService {


    Role getAdmin();

    Role getUser();

    Role getRole(String role);
}
