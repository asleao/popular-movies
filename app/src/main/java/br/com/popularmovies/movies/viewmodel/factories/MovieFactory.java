package br.com.popularmovies.movies.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.popularmovies.movies.viewmodel.MovieViewModel;
import br.com.popularmovies.services.movieService.MovieRepositoryImpl;

public class MovieFactory implements ViewModelProvider.Factory {
    private final MovieRepositoryImpl mMovieRepository;

    public MovieFactory(MovieRepositoryImpl mMovieRepository) {
        this.mMovieRepository = mMovieRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(mMovieRepository);
    }
}
