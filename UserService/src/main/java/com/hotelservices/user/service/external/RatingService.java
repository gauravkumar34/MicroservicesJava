package com.hotelservices.user.service.external;

import com.hotelservices.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {
    @GetMapping("/ratings/users/{userId}")
    Rating[] getRatingsByUserId(@PathVariable("userId") String userId);

}
