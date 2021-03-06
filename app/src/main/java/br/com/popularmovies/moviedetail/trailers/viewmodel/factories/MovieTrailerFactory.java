package br.com.popularmovies.moviedetail.trailers.viewmodel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.popularmovies.moviedetail.trailers.viewmodel.MovieTrailerViewModel;
import br.com.popularmovies.services.movieService.MovieRepositoryImpl;

public class MovieTrailerFactory implements ViewModelProvider.Factory {
    private final int movieId;
    private final MovieRepositoryImpl mMovieRepository;

    public MovieTrailerFactory(MovieRepositoryImpl mMovieRepository, int movieId) {
        this.mMovieRepository = mMovieRepository;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieTrailerViewModel(mMovieRepository, this.movieId);
    }
}
