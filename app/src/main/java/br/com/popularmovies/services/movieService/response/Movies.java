package br.com.popularmovies.services.movieService.response;


import com.squareup.moshi.Json;

import java.util.List;

public class Movies {

    @Json(name = "results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
