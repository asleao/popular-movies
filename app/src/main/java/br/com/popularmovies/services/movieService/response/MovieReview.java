package br.com.popularmovies.services.movieService.response;

import com.squareup.moshi.Json;

public class MovieReview {
    @Json(name = "author")
    private int author;

    @Json(name = "content")
    private int content;
}
