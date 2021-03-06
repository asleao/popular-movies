package br.com.popularmovies.moviedetail.reviews.viewModel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.source.MovieRepository;

public class MovieReviewViewModel extends ViewModel {
    private final LiveData<Resource<MovieReviews>> reviews;
    private final MutableLiveData<Integer> movieId = new MutableLiveData<>();

    public MovieReviewViewModel(final MovieRepository mMovieRepository, int movieId) {
        reviews = Transformations.switchMap(this.movieId, new Function<Integer, LiveData<Resource<MovieReviews>>>() {
            @Override
            public LiveData<Resource<MovieReviews>> apply(Integer input) {
                return input != null ? mMovieRepository.getMovieReviews(input) : null;
            }
        });
        this.movieId.setValue(movieId);
    }

    public LiveData<Resource<MovieReviews>> getReviews() {
        return reviews;
    }

    public void tryAgain() {
        movieId.setValue(movieId.getValue());
    }
}
