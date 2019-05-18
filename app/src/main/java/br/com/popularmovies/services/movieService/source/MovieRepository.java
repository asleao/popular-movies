package br.com.popularmovies.services.movieService.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private MovieDataSource mMovieLocalDataSource;
    private MovieDataSource mMovieRemoteDataSource;


    private MovieRepository(@NonNull MovieDataSource mMovieLocalDataSource, @NonNull MovieDataSource mMovieRemoteDataSource) {
        this.mMovieLocalDataSource = mMovieLocalDataSource;
        this.mMovieRemoteDataSource = mMovieRemoteDataSource;
    }

    public static MovieRepository getInstance(MovieDataSource mMovieLocalDataSource, MovieDataSource mMovieRemoteDataSource) {
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(mMovieLocalDataSource, mMovieRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public LiveData<Resource<Movies>> getMovies(final String orderBy) {
        return mMovieRemoteDataSource.getMovies(orderBy);
    }

    @Override
    public LiveData<Resource<MovieReviews>> getMovieReviews(int movieId) {
        return mMovieRemoteDataSource.getMovieReviews(movieId);
    }

    @Override
    public LiveData<Resource<Boolean>> saveToFavorites(int movieId, boolean status) {
        return mMovieLocalDataSource.saveToFavorites(movieId, status);
    }

    @Override
    public LiveData<Resource<Void>> saveMovies(List<Movie> movies) {
        return mMovieLocalDataSource.saveMovies(movies);
    }

    @Override
    public LiveData<Resource<Void>> saveMovie(Movie movie) {
        return mMovieLocalDataSource.saveMovie(movie);
    }

    @Override
    public LiveData<Resource<Void>> removeMovie(Movie movie) {
        return mMovieLocalDataSource.removeMovie(movie);
    }
}
