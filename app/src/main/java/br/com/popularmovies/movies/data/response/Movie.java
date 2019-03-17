package br.com.popularmovies.movies.data.response;

import com.squareup.moshi.Json;

import java.math.BigDecimal;

public class Movie {

    @Json(name = "vote_count")
    private int votes;

    @Json(name = "id")
    private int id;

    @Json(name = "vote_coverage")
    private BigDecimal voteAverage;

    @Json(name = "original_title")
    private String originalTitle;

    @Json(name = "popularity")
    private BigDecimal popularity;

    @Json(name = "poster_path")
    private String poster;

    @Json(name = "overview")
    private String overview;

    @Json(name = "release_date")
    private String releaseDate;

    public String getOriginalTitle() {
        return originalTitle;
    }
}
