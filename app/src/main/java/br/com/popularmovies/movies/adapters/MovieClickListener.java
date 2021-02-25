package br.com.popularmovies.movies.adapters;

import br.com.popularmovies.services.movieService.response.MovieDto;

public interface MovieClickListener {
    void onMovieClick(MovieDto movie);
}
