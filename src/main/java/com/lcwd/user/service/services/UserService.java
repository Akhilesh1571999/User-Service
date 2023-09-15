package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {


    User saveUser(User user);

    List<User> getAllUser();

    User getUserById(String userId);

}
