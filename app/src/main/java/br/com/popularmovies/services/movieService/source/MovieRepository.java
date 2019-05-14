package br.com.popularmovies.services.movieService.source;

import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.popularmovies.data.CacheControl;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;
import br.com.popularmovies.utils.AppExecutors;

import static br.com.popularmovies.data.Constants.CACHE_TIMEOUT;
import static br.com.popularmovies.movies.Constants.CONNECTION_MSG_ERROR;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_MESSAGE;

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
                                || mCacheControl.shouldFetch(orderBy)
                                && isOnline()) {
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
                            if (!moviesResource.data.getMovies().isEmpty()) {
                                movies.postValue(moviesResource);
                            } else {
                                ErrorResponse error = new ErrorResponse(503,
                                        CONNECTION_MSG_ERROR);
                                movies.postValue(Resource.<Movies>error(error));
                            }
                        }
                        break;
                    case ERROR:
                        movies.postValue(moviesResource);
                        break;
                }
            }
        });
        return movies;
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
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
