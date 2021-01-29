package com.microservices.movieinfoservice.controller;

import com.microservices.movieinfoservice.model.Movie;
import com.microservices.movieinfoservice.model.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResourceController {

    @Value("${api.key}")
    private  String apiKey;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @RequestMapping("/{movieId}")
    public Movie getMovie(@PathVariable("movieId") String movieId) throws InterruptedException{

        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey, MovieSummary.class);
		/*
		 * MovieSummary movieSummary =new MovieSummary(); movieSummary.setId("5");
		 * movieSummary.setOverview(""); movieSummary.setTitle("");
		 */
        
        Thread.sleep(500);
        return  new Movie(movieId,movieSummary.getTitle(),movieSummary.getOverview(),movieSummary.getPopularity());

    }
}
