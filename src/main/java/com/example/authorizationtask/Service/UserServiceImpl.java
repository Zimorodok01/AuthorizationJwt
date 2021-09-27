package com.example.authorizationtask.Service;

import com.example.authorizationtask.Entity.Role;
import com.example.authorizationtask.Entity.User;
import com.example.authorizationtask.Entity.dto.UserDto;
import com.example.authorizationtask.Repository.UserRepo;
import com.example.authorizationtask.Service.jwt.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final TokenService tokenService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final String USERNAME_NOT_FOUND = "User with username %s doesn't exist";
    private final String USERNAME_EXISTS = "Username %s is already exists";
    private final String USER_DOES_NOT_EXIST = "User with %d doesn't exist";

    @Autowired
    public UserServiceImpl(UserRepo userRepo, TokenService tokenService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.tokenService = tokenService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND,username)));
    }

    @Override
    public List<UserDto> getUsers(String token) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");
        log.info("Getting all users from database");
        List<UserDto> userDtos = new ArrayList<>();
        userRepo.findAll().forEach(user -> userDtos.add(user.getDto()));
        return userDtos;
    }

    @Override
    public UserDto getUserByToken(String token) {
        log.info("Getting username from token");
        String username = tokenService.readAccessToken(token);
        log.info("Find user by username");
        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new ResponseStatusException(BAD_REQUEST,
                            String.format(USERNAME_NOT_FOUND,username))
                ).getDto();
    }

    @Override
    public UserDto addUser(String token, UserDto userDto) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        if (userRepo.findByUsername(userDto.getUsername()).isPresent()) {
            log.error("Username is already exists");
            throwBadException(String.format(USERNAME_EXISTS,userDto.getUsername()));
        }

        if (userDto.getUsername() == null) {
            log.error("Username is null");
            throwBadException("Username is required");
        }

        if (userDto.getName() == null) {
            log.error("Name is null");
            throwBadException("User doesn't have name");
        }

        if (userDto.getSurname() == null) {
            log.error("Surname is null");
            throwBadException("Surname is null");
        }

        if (userDto.getRole() == null) {
            log.error("Role is null");
            throwBadException("Role is null");
        }

        if (userDto.getPassword() == null) {
            log.error("Password is null");
            throwBadException("Password is required");
        }

        log.info("Saving new User");
        User user = new User(userDto);
        user.setRole(roleService.getRole(userDto.getRole()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);
        log.info("User is saved");
        return user.getDto();
    }

    @Override
    public UserDto updateUser(String token, Long id, String name, String surname, String username, String password, String role) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        BAD_REQUEST,
                        String.format(USER_DOES_NOT_EXIST,id))
        );

        log.info("Updating user");
        if (name != null) {
            log.info("Updating name");
            user.setName(name);
        }

        if (surname != null) {
            log.info("Updating surname");
            user.setSurname(surname);
        }

        if (username != null) {
            if (userRepo.findByUsername(username).isPresent()) {
                log.error("Username is already exists");
                throwBadException(String.format(USERNAME_EXISTS,username));
            }
            log.info("Updating username");
            user.setUsername(username);
        }

        if (password != null) {
            log.info("Updating password");
            user.setPassword(passwordEncoder.encode(password));
        }

        if (role != null) {
            log.info("Updating role");
            user.setRole(roleService.getRole(role));
        }
        userRepo.save(user);

        return user.getDto();
    }

    @Override
    public UserDto deleteUser(String token, Long id) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        BAD_REQUEST,
                        String.format(USER_DOES_NOT_EXIST,id))
        );

        log.info("Deleting user");
        userRepo.delete(user);
        log.info("User is deleted");
        return user.getDto();
    }

    @Override
    public List<UserDto> getUsersSortedByName(String token) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        log.info("Retrieving all users ordered by name");
        List<User> users = userRepo.findByOrderByNameAsc();
        List<UserDto> userDtoList = new LinkedList<>();
        users.forEach(user -> {
            userDtoList.add(user.getDto());
        });
        log.info("Users are retrieved");

        return userDtoList;
    }

    @Override
    public List<UserDto> getUsersSortedBySurname(String token) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        log.info("Retrieving all users ordered by surname");
        List<User> users = userRepo.findByOrderBySurnameAsc();
        List<UserDto> userDtoList = new LinkedList<>();
        users.forEach(user -> {
            userDtoList.add(user.getDto());
        });
        log.info("Users are retrieved");

        return userDtoList;
    }

    @Override
    public List<UserDto> getUsersSortedByUsername(String token) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        log.info("Retrieving all users ordered by username");
        List<User> users = userRepo.findByOrderByUsernameAsc();
        List<UserDto> userDtoList = new LinkedList<>();
        users.forEach(user -> {
            userDtoList.add(user.getDto());
        });
        log.info("Users are retrieved");

        return userDtoList;
    }

    @Override
    public List<UserDto> getAdminRoles(String token) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        log.info("Get all admins from database");
        Role role = roleService.getAdmin();
        List<UserDto> users = new LinkedList<>();
        userRepo.findByRole(role).forEach(user -> {
            users.add(user.getDto());
        });

        return users;
    }

    @Override
    public List<UserDto> getUsersRole(String token) {
        log.info("Reading token");
        tokenService.readAccessToken(token);
        log.info("Token is read");

        Role role = roleService.getUser();
        List<UserDto> users = new LinkedList<>();
        userRepo.findByRole(role).forEach(user -> {
            users.add(user.getDto());
        });

        return users;
    }

    private void throwBadException(String message) {
        throw new ResponseStatusException(BAD_REQUEST, message);
    }
}
