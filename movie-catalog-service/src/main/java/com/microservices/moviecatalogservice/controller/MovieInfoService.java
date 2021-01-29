package com.microservices.moviecatalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.moviecatalogservice.model.Movie;
import com.microservices.moviecatalogservice.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfoService {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackMovie",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "5"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50")
	})
	public Movie getMovie(Rating rating) {
		return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId() ,Movie.class);
	}

	public Movie getFallbackMovie(Rating rating) {
		return new Movie("null","Not found","null");
	}
	
}
