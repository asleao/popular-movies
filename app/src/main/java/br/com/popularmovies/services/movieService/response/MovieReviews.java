package br.com.popularmovies.services.movieService.response;

import com.squareup.moshi.Json;

public class MovieReviews {
    @Json(name = "results")
    private int results;
}
