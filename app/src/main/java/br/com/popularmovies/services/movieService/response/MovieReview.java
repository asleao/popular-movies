package br.com.popularmovies.services.movieService.response;

import com.squareup.moshi.Json;

public class MovieReview {
    @Json(name = "author")
    private String author;

    @Json(name = "content")
    private String content;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
