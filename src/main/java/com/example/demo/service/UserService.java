package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    List<Role> getAllRoles();

    User createNewUser( User user);

    User getUserById (Long pid);

    User updateUser (User user);

    void deleteUserById(Long pid);

    List<User> filter(String filterStr);

    Optional<User> getUserByLogin (String login);
}
