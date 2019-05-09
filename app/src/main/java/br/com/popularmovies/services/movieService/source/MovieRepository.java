package br.com.popularmovies.services.movieService.source;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private MovieDataSource mMovieDataSource;

    private MovieRepository(@NonNull MovieDataSource mMovieDataSource) {
        this.mMovieDataSource = mMovieDataSource;
    }

    public static MovieRepository getInstance(MovieDataSource mMovieDataSource) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(mMovieDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<Resource<Movies>> getMovies(String orderBy) {
        return mMovieDataSource.getMovies(orderBy);
    }

    @Override
    public LiveData<Resource<MovieReviews>> getMovieReviews(int movieId) {
        return mMovieDataSource.getMovieReviews(movieId);
    }
}
