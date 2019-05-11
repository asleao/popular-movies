package br.com.popularmovies.services.movieService.source;

import androidx.lifecycle.LiveData;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;

public interface MovieDataSource {

    LiveData<Resource<Movies>> getMovies(String orderBy);

    LiveData<Resource<MovieReviews>> getMovieReviews(int movieId);

    LiveData<Resource<Boolean>> saveMovie(Movie movie);
}
