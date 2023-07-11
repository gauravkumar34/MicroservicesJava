package com.hotelservices.rating.services;

import com.hotelservices.rating.entities.Rating;
import com.hotelservices.rating.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RatingService {

    //create

    Rating create(Rating rating);

    //get all ratings

    List<Rating> getRatings();

    //get all by userid
    List<Rating> getRatingByUserId(String userId);

    // get all by hotel
    List<Rating> getRatingByHotelId(String hotelId);
}
