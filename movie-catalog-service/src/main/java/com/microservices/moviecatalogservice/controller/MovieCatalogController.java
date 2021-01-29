package com.microservices.moviecatalogservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.moviecatalogservice.model.Movie;
import com.microservices.moviecatalogservice.model.MovieCatalog;
import com.microservices.moviecatalogservice.model.Rating;
import com.microservices.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webclient;
	
	@Autowired
	private	MovieInfoService movieInfoService; 
	
	@Autowired
	private UserRatingService userRatingService;
	
	@RequestMapping("/{userId}")
	//@HystrixCommand(fallbackMethod = "getFallBackCatalog")
	public List<MovieCatalog> getCatalog(@PathVariable("userId")String userId){
		
		
		UserRating userRating=userRatingService.getUserRatings(userId);
		System.out.println(userRating.toString());
		//get all rated movie ids
				// for each movie id , call movie info service and get details
				//put them all together

		// API call using RestTemplate
		
		
		  return userRating.getUserRating().stream().map(rating-> { Movie movie =movieInfoService.getMovie(rating); 
		  return new MovieCatalog(movie.getMovieName(),movie.getDesc(),rating.getRating()); })
		  .collect(Collectors.toList());
		 
		
		//API call using WebClient
		
		/*
		 * return userRating.getUserRating().stream().map(rating->{
		 * 
		 * Movie movie = webclient.build() .get()
		 * .uri("http://movie-info-service/movies/" +rating.getMovieId()) .retrieve()
		 * .bodyToMono(Movie.class) .block();
		 * 
		 * return new
		 * MovieCatalog(movie.getMovieName(),movie.getDesc(),rating.getRating());
		 * }).collect(Collectors.toList());
		 */
		 
	}

	@HystrixCommand(fallbackMethod = "getFallbackMovie")
	private Movie getMovie(Rating rating) {
		return restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId() ,Movie.class);
	}

	@HystrixCommand(fallbackMethod = "getFallbackUserRatings")
	private UserRating getUserRatings(String userId) {
		return restTemplate.getForObject("http://rating-service/ratingsdata/users/"+userId, UserRating.class);
	}
	
	private Movie getFallbackMovie(Rating rating) {
		return new Movie("null","Not found","null");
	}
	
	private UserRating getFallbackUserRatings(String userId) {
		Rating rating=new Rating();
		rating.setMovieId("null");
		rating.setRating(-1);
		return new UserRating(Arrays.asList(rating));
	}
	
	/*
	 * public List<MovieCatalog> getFallBackCatalog(@PathVariable("userId")String
	 * userId){
	 * 
	 * return Arrays.asList(new MovieCatalog("Not found","",0)); }
	 */
}
