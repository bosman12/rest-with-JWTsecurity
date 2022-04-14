package com.example.rest_example.service.impl;

import com.example.rest_example.entity.User;
import com.example.rest_example.repositrory.UserRepository;
import com.example.rest_example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Override
    public List<User> getAllUsers(){
        List<User> result = userRepository.findAll();
        log.info("total found users: {}",result);
        return result;
    }

    @Override
    public User findUserById(Long id){
        User result = userRepository.getById(id);
        log.info("found a user: {}",result);
        return result;
    }

    @Override
    public void saveUser(User user) {
        log.info("saved user {}", user);
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id){
        log.info("user with id {} has been deleted", id);
        userRepository.deleteById(id);
    }




}
