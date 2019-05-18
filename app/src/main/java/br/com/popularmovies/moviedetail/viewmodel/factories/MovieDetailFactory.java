package br.com.popularmovies.moviedetail.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.source.MovieRepository;

public class MovieDetailFactory implements ViewModelProvider.Factory {
    private Movie movie;
    private MovieRepository mMovieRepository;

    public MovieDetailFactory(MovieRepository mMovieRepository, Movie movie) {
        this.movie = movie;
        this.mMovieRepository = mMovieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(mMovieRepository, movie);
    }
}
