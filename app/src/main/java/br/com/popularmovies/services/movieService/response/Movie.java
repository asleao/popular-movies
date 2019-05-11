package br.com.popularmovies.services.movieService.response;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

@Entity
public class Movie {

    @Json(name = "vote_count")
    private int votes;

    @PrimaryKey(autoGenerate = true)
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
    private LocalDate releaseDate;

    private boolean isFavorite;

    @Ignore
    public Movie(int votes, BigDecimal voteAverage, String originalTitle, BigDecimal popularity, String poster, String overview, LocalDate releaseDate, boolean isFavorite) {
        this.votes = votes;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.poster = poster;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
    }

    public Movie(int votes, int id, BigDecimal voteAverage, String originalTitle, BigDecimal popularity, String poster, String overview, LocalDate releaseDate, boolean isFavorite) {
        this.votes = votes;
        this.id = id;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.poster = poster;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
