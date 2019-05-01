package br.com.popularmovies.moviedetail.reviews.viewModel.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

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
