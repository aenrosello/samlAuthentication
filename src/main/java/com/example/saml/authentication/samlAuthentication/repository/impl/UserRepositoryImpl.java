package com.example.saml.authentication.samlAuthentication.repository.impl;

import com.example.saml.authentication.samlAuthentication.entity.User;
import com.example.saml.authentication.samlAuthentication.repository.UserRepository;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository<Integer, User> {

    private Map<Integer, User> userMap;

    public UserRepositoryImpl() {
        userMap = new HashMap<>();
        int amountOfUsers = 100;
        DataFactory factory = new DataFactory();
        for (int i = 0; i < amountOfUsers; i++) {
            User user = new User();
            user.setId(i);
            user.setName(factory.getName());
            user.setEmail(factory.getEmailAddress());
            userMap.put(i, user);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        if (!userMap.isEmpty()) {
            userList.addAll(userMap.values());
        }
        return userList;
    }

    @Override
    public User findOne(Integer id) {
        User user = null;
        if (userMap.containsKey(id)) {
            user = userMap.get(id);
        }
        return user;
    }
}
