package com.microservices.movieinfoservice.model;

public class Movie {

    private String movieId;
    private String movieName;
    private  String desc;
    private String popularity;
    
    public Movie(String movieId, String movieName,String desc,String popularity) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.desc=desc;
        this.popularity=popularity;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

	public String getPopularity() {
		return popularity;
	}

	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}
    
    
}
