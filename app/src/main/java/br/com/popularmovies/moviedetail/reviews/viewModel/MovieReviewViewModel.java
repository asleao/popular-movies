package br.com.popularmovies.moviedetail.reviews.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;

public class MovieReviewViewModel extends ViewModel {
    private MovieRepository mMovieRepository;
    private LiveData<Resource<MovieReviews>> mReviews;


    public MovieReviewViewModel() {
        mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mReviews = mMovieRepository.getMovieReviews(299537);
    }

    public LiveData<Resource<MovieReviews>> getmReviews() {
        return mReviews;
    }
}
