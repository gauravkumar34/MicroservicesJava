package com.hotelservices.user.service.services.impl;

import com.hotelservices.user.service.entities.Hotel;
import com.hotelservices.user.service.entities.Rating;
import com.hotelservices.user.service.entities.User;
import com.hotelservices.user.service.exceptions.ResourceNotFoundException;
import com.hotelservices.user.service.external.HotelService;
import com.hotelservices.user.service.external.RatingService;
import com.hotelservices.user.service.repositories.UserRepository;
import com.hotelservices.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;
    @Autowired
    private RatingService ratingService;
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
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(("User with given id is not found on server !! :"+ userId)));
        //fetch rating of the above user from RATING SERVICE
        //http://localhost:8083/ratings/users/4d98377f-d208-4a1a-bf9b-4492e09b6a60
//        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        // 2nd method with FeignClient
        Rating[] ratingsOfUser =  ratingService.getRatingsByUserId(user.getUserId());
        logger.info("{}",ratingsOfUser);
       List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get hotel
             // http://localhost:8082/hotels/977e29f9-d5aa-4e26-844a-048aea646448
            // 2nd method with FeignClient
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            logger.info("response status code : {}",forEntity.getStatusCode());
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            //set the hotel to rating
            rating.setHotel(hotel);
            //return rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
