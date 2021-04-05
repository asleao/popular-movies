package br.com.popularmovies.moviedetail.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel;
import br.com.popularmovies.services.movieService.MovieRepositoryImpl;

public class MovieDetailFactory implements ViewModelProvider.Factory {
    private final int movieId;
    private final MovieRepositoryImpl mMovieRepository;

    public MovieDetailFactory(MovieRepositoryImpl mMovieRepository, int movieId) {
        this.movieId = movieId;
        this.mMovieRepository = mMovieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieDetailViewModel(mMovieRepository, movieId);
    }
}
