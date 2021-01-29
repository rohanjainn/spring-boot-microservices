package com.microservices.moviecatalogservice.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.moviecatalogservice.model.Rating;
import com.microservices.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingService {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRatings",
			commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "5"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50")
			})
	public UserRating getUserRatings(String userId) {
		return restTemplate.getForObject("http://rating-service/ratingsdata/users/"+userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRatings(String userId) {
		Rating rating=new Rating();
		rating.setMovieId("null");
		rating.setRating(-1);
		return new UserRating(Arrays.asList(rating));
	}
}
