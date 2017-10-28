package com.example.saml.authentication.samlAuthentication.controller;

import com.example.saml.authentication.samlAuthentication.entity.User;
import com.example.saml.authentication.samlAuthentication.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private Logger log = Logger.getLogger(UserController.class);
    private UserRepository<Integer, User> userRepository;

    @Autowired
    public UserController(UserRepository<Integer, User> userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List getUsers() {
        log.info("get all users");
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        log.info("get user by id " + id);
        return userRepository.findOne(id);
    }
}
