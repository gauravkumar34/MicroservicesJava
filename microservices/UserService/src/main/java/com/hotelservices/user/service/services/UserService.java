package com.hotelservices.user.service.services;

import com.hotelservices.user.service.entities.User;

import java.util.List;

public interface UserService {

    //create
    User saveUser(User user);

    //get all users
    List<User> getAllUser();

    //get single user of given userid
    User getUser(String userId);
}
