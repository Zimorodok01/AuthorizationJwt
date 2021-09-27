package com.example.authorizationtask.Repository;

import com.example.authorizationtask.Entity.Role;
import com.example.authorizationtask.Entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    User findById(long id);

    Optional<User> findByUsername(String username);

    List<User> findByOrderByNameAsc();
    List<User> findByOrderBySurnameAsc();
    List<User> findByOrderByUsernameAsc();

    List<User> findByRole(Role role);



}
