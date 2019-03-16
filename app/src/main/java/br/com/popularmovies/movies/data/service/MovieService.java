package br.com.popularmovies.movies.data.service;

import br.com.popularmovies.movies.data.response.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("discover/movie/")
    Call<Movies> getMovies(@Query("sort_by") String orderBy);


}
