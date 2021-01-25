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

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webclient;
	
	@RequestMapping("/{userId}")
	public List<MovieCatalog> getCatalog(@PathVariable("userId")String userId){
		
		
		
		/*
		 * List<Rating> ratings=Arrays.asList( new Rating(4,"m3"),new Rating(3,"m2"));
		 */
		
		
		UserRating userRating=restTemplate.getForObject("http://rating-service/ratingsdata/users/1", UserRating.class);
		System.out.println(userRating.toString());
		//get all rated movie ids
				// for each movie id , call movie info service and get details
				//put them all together

		// API call using RestTemplate
		
		/*
		 * return userRating.getUserRating().stream().map(rating-> { Movie movie =
		 * restTemplate.getForObject("http://movie-info-service/movies/movies/"+rating.
		 * getMovieId() ,Movie.class); return new
		 * MovieCatalog(movie.getMovieName(),"desc",rating.getRating()); })
		 * .collect(Collectors.toList());
		 */
		
		//API call using WebClient
		
		  return userRating.getUserRating().stream().map(rating->{
		  
		  Movie movie = webclient.build() .get()
		  .uri("http://movie-info-service/movies/" +rating.getMovieId()) .retrieve()
		  .bodyToMono(Movie.class) .block();
		  
		  return new MovieCatalog(movie.getMovieName(),"desc",rating.getRating());
		  }).collect(Collectors.toList());
		 
	
	}
}
