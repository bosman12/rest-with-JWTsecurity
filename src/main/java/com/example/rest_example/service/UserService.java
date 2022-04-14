package com.example.rest_example.service;

import com.example.rest_example.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User findUserById(Long id);

    void saveUser(User user);

    void deleteUserById(Long id);


}
