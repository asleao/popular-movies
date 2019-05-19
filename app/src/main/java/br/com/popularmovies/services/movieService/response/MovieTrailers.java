package br.com.popularmovies.services.movieService.response;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieTrailers {
    @Json(name = "results")
    private List<MovieTrailer> trailers;

    public List<MovieTrailer> getTrailers() {
        return trailers;
    }
}
