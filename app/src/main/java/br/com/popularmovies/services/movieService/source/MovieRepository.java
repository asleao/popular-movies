package br.com.popularmovies.services.movieService.source;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.popularmovies.data.CacheControl;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;

import static br.com.popularmovies.data.Constants.CACHE_TIMEOUT;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private MovieDataSource mMovieLocalDataSource;
    private MovieDataSource mMovieRemoteDataSource;

    private CacheControl mCacheControl = new CacheControl(CACHE_TIMEOUT);

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
        final MediatorLiveData<Resource<Movies>> movies = new MediatorLiveData<>();
        final LiveData<Resource<Movies>> dbSource = mMovieLocalDataSource.getMovies(orderBy);
        final LiveData<Resource<Movies>> networkSource = mMovieRemoteDataSource.getMovies(orderBy);
        movies.addSource(dbSource, new Observer<Resource<Movies>>() {
            @Override
            public void onChanged(Resource<Movies> moviesResource) {
                switch (moviesResource.status) {
                    case SUCCESS:
                        if ((moviesResource.data.getMovies() == null)
                                || mCacheControl.shouldFetch(orderBy)) {
                            movies.removeSource(dbSource);
                            movies.addSource(networkSource, new Observer<Resource<Movies>>() {
                                @Override
                                public void onChanged(Resource<Movies> moviesResource) {
                                    mCacheControl.addRequest(orderBy, SystemClock.uptimeMillis());
                                    if (moviesResource.status == Resource.Status.SUCCESS &&
                                            moviesResource.data != null &&
                                            moviesResource.data.getMovies() != null) {
                                        saveMovies(moviesResource.data.getMovies());
                                    }
                                    movies.postValue(moviesResource);
                                }
                            });
                        } else {
                            movies.postValue(moviesResource);
                        }
                        break;
                    case ERROR:
                        break;
                }
            }
        });
        return movies;
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
}
