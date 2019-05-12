package br.com.popularmovies.services.movieService.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

@Entity
public class Movie implements Parcelable {

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

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Ignore
    public Movie(Parcel in) {
        this.votes = in.readInt();
        this.id = in.readInt();
        this.voteAverage = (BigDecimal) in.readSerializable();
        this.originalTitle = in.readString();
        this.popularity = (BigDecimal) in.readSerializable();
        this.poster = in.readString();
        this.overview = in.readString();
        this.releaseDate = (LocalDate) in.readSerializable();
        this.isFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.votes);
        dest.writeInt(this.id);
        dest.writeSerializable(this.voteAverage);
        dest.writeString(this.originalTitle);
        dest.writeSerializable(this.popularity);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
        dest.writeSerializable(this.releaseDate);
        dest.writeByte((byte) (this.isFavorite ? 1 : 0));

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
