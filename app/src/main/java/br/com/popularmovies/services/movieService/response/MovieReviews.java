package br.com.popularmovies.services.movieService.response;

import com.squareup.moshi.Json;

import java.util.List;

public class MovieReviews {
    @Json(name = "results")
    private List<MovieReview> reviews;

    public List<MovieReview> getReviews() {
        return reviews;
    }
}
