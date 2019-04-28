package br.com.popularmovies.services.movieService.service;

import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("discover/movie/")
    Call<Movies> getMovies(@Query("sort_by") String orderBy);

    @GET("discover/{id}/reviews")
    Call<MovieReviews> getMovieReviews(@Path("id") int movieId);
}
