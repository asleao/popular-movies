package br.com.popularmovies.services.movieService.source.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.popularmovies.data.local.AppDatabase;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.OldResource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.MovieTrailers;
import br.com.popularmovies.services.movieService.response.Movies;
import br.com.popularmovies.services.movieService.source.MovieDataSource;
import br.com.popularmovies.utils.AppExecutors;

import static br.com.popularmovies.core.network.NetworkConstantsKt.GENERIC_ERROR_CODE;
import static br.com.popularmovies.movies.Constants.ROOM_MSG_ERROR;

public class MovieLocalDataSource implements MovieDataSource {
    private volatile static MovieLocalDataSource INSTANCE = null;
    private final MovieDao mMovieDao;

    private MovieLocalDataSource(Context context) {
        this.mMovieDao = AppDatabase.getInstance(context).movieDao();
    }

    public static MovieLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieLocalDataSource(context);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public LiveData<OldResource<Movies>> getMovies(final String orderBy) {
        final MediatorLiveData<OldResource<Movies>> movies = new MediatorLiveData<>();
        movies.postValue(OldResource.
                <Movies>loading());
        try {
            movies.addSource(mMovieDao.getMovies(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> fetchedMovies) {
                    movies.postValue(OldResource.success(new Movies(fetchedMovies)));
                }
            });

        } catch (Exception e) {
            movies.postValue(OldResource.<Movies>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return movies;
    }

    @Override
    public LiveData<OldResource<Movie>> getMovie(final int movieId) {
        final MediatorLiveData<OldResource<Movie>> movie = new MediatorLiveData<>();
        movie.postValue(OldResource.
                <Movie>loading());
        try {
            movie.addSource(mMovieDao.getMovie(movieId), new Observer<Movie>() {
                @Override
                public void onChanged(Movie fetchedMovie) {
                    movie.postValue(OldResource.success(fetchedMovie));
                }
            });

        } catch (Exception e) {
            movie.postValue(OldResource.<Movie>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return movie;
    }

    @Override
    public LiveData<OldResource<MovieReviews>> getMovieReviews(int movieId) {
        return null;
    }

    @Override
    public LiveData<OldResource<Boolean>> saveToFavorites(final int movieId, final boolean status) {
        final MutableLiveData<OldResource<Boolean>> mMovie = new MutableLiveData<>();
        mMovie.postValue(OldResource.
                <Boolean>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.saveFavorites(movieId, status);
                    mMovie.postValue(OldResource.
                            success(status));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(OldResource.<Boolean>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<OldResource<Void>> saveMovies(final List<Movie> movies) {
        final MutableLiveData<OldResource<Void>> mMovie = new MutableLiveData<>();
        mMovie.postValue(OldResource.
                <Void>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.insertAllMovies(movies);
                    mMovie.postValue(OldResource.<Void>
                            success(null));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(OldResource.<Void>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<OldResource<Void>> saveMovie(final Movie movie) {
        final MutableLiveData<OldResource<Void>> mMovie = new MutableLiveData<>();
        mMovie.postValue(OldResource.
                <Void>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.insertMovie(movie);
                    mMovie.postValue(OldResource.<Void>
                            success(null));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(OldResource.<Void>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<OldResource<Void>> removeMovie(final Movie movie) {
        final MutableLiveData<OldResource<Void>> mMovie = new MutableLiveData<>();
        mMovie.postValue(OldResource.
                <Void>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.deleteMovie(movie);
                    mMovie.postValue(OldResource.<Void>
                            success(null));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(OldResource.<Void>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<OldResource<MovieTrailers>> getMovieTrailers(int movieId) {
        return null;
    }

}
