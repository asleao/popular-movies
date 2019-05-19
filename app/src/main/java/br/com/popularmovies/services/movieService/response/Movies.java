package br.com.popularmovies.services.movieService.response;


import com.squareup.moshi.Json;

import java.util.List;

public class Movies {

    @Json(name = "results")
    private final List<Movie> movies;

    public Movies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
