package br.com.popularmovies.movies.data.response;

import com.squareup.moshi.Json;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class Movie {

    @Json(name = "vote_count")
    private int votes;

    @Json(name = "id")
    private int id;

    @Json(name = "vote_average")
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
    private DateTime releaseDate;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPoster() {
        return poster;
    }

    public int getVotes() {
        return votes;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public DateTime getReleaseDate() {
        return releaseDate;
    }
}
