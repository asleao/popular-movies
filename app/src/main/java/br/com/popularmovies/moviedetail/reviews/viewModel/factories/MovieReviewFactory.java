package br.com.popularmovies.moviedetail.reviews.viewModel.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel;
import br.com.popularmovies.services.movieService.source.MovieRepository;

public class MovieReviewFactory implements ViewModelProvider.Factory {
    private int movieId;
    private MovieRepository mMovieRepository;

    public MovieReviewFactory(MovieRepository mMovieRepository, int movieId) {
        this.mMovieRepository = mMovieRepository;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieReviewViewModel(mMovieRepository, this.movieId);
    }
}
