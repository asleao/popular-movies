package br.com.popularmovies.moviedetail.reviews.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;

public class MovieReviewViewModel extends ViewModel {
    private LiveData<Resource<MovieReviews>> reviews;

    public MovieReviewViewModel(int movieId) {
        MovieRepository mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        reviews = mMovieRepository.getMovieReviews(movieId);
    }

    public LiveData<Resource<MovieReviews>> getReviews() {
        return reviews;
    }
}
