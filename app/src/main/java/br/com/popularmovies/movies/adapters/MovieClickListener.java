package br.com.popularmovies.movies.adapters;

import br.com.popularmovies.services.movieService.response.Movie;

public interface MovieClickListener {
    void onMovieClick(Movie movie);
}
