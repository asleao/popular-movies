package br.com.popularmovies.services.movieService.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.MovieTrailers;
import br.com.popularmovies.services.movieService.response.Movies;

public interface MovieDataSource {

    LiveData<Resource<Movies>> getMovies(String orderBy);

    LiveData<Resource<Movie>> getMovie(int movieId);

    LiveData<Resource<MovieReviews>> getMovieReviews(int movieId);

    LiveData<Resource<Boolean>> saveToFavorites(int movieId, boolean status);

    LiveData<Resource<Void>> saveMovies(List<Movie> movies);

    LiveData<Resource<Void>> saveMovie(Movie movie);

    LiveData<Resource<Void>> removeMovie(Movie movie);

    LiveData<Resource<MovieTrailers>> getMovieTrailers(int movieId);
}
