package br.com.popularmovies.services.movieService.service;

import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.MovieTrailers;
import br.com.popularmovies.services.movieService.response.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

    @GET("movie/{sort_by}")
    Call<Movies> getMovies(@Path("sort_by") String orderBy);

    @GET("movie/{id}/reviews")
    Call<MovieReviews> getMovieReviews(@Path("id") int movieId);

    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") int movieId);

    @GET("movie/{id}/videos")
    Call<MovieTrailers> getMovieTrailers(@Path("id") int movieId);
}
