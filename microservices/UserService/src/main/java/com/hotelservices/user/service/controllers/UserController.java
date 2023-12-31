package com.hotelservices.user.service.controllers;

import com.hotelservices.user.service.entities.User;
import com.hotelservices.user.service.services.UserService;
import com.hotelservices.user.service.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('Admin')")
    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody  User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    @GetMapping("/{userId}")
  //  @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
  //  @Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("Retry Count {}",retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    int retryCount=1;
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
//        logger.info("Fallback is excuted because service is down : ",ex.getMessage());

        ex.printStackTrace();
        logger.info("Retry Count {}",retryCount);
      User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service is down")
                .userId("123456")
                .build();
        return new  ResponseEntity<>(user,HttpStatus.OK);
    }

    //all user get

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }
}
