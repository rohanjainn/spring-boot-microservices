package com.microservices.moviecatalogservice.model;

public class Rating {

    private int rating;
    private String movieId;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public Rating(int rating, String movieId) {
        this.rating = rating;
        this.movieId = movieId;
    }

	public Rating() {
		super();
	}
    
    
}
