package com.hotelservices.user.service.services.impl;

import com.hotelservices.user.service.entities.User;
import com.hotelservices.user.service.exceptions.ResourceNotFoundException;
import com.hotelservices.user.service.repositories.UserRepository;
import com.hotelservices.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(("User with given id is not found on server !! :"+ userId)));
    }
}
