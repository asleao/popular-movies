package br.com.popularmovies.moviedetail.reviews.viewModel.factories;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import br.com.popularmovies.moviedetail.reviews.viewModel.MovieReviewViewModel;

public class MovieReviewFactory implements ViewModelProvider.Factory {
    private int movieId;

    public MovieReviewFactory(int movieId) {
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieReviewViewModel(this.movieId);
    }
}
