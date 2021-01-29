package com.microservices.ratingdataservice.controller;

import com.microservices.ratingdataservice.model.Rating;
import com.microservices.ratingdataservice.model.UserRating;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/ratingsdata")
@RestController
public class RatingDataController {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){

        return  new Rating(4,movieId);

    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId){

        List<Rating> ratings=Arrays.asList(
          new Rating(4,"123"),
          new Rating(3,"65")
        );
        UserRating userRating=new UserRating();
        userRating.setUserRating(ratings);
        return  userRating;
    }

}
