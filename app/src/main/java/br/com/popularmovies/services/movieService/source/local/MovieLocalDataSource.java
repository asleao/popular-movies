package br.com.popularmovies.services.movieService.source.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.popularmovies.data.local.AppDatabase;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.MovieTrailers;
import br.com.popularmovies.services.movieService.response.Movies;
import br.com.popularmovies.services.movieService.source.MovieDataSource;
import br.com.popularmovies.utils.AppExecutors;

import static br.com.popularmovies.data.Constants.GENERIC_ERROR_CODE;
import static br.com.popularmovies.movies.Constants.ROOM_MSG_ERROR;

public class MovieLocalDataSource implements MovieDataSource {
    private volatile static MovieLocalDataSource INSTANCE = null;
    private MovieDao mMovieDao;

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
    public LiveData<Resource<Movies>> getMovies(final String orderBy) {
        final MediatorLiveData<Resource<Movies>> movies = new MediatorLiveData<>();
        movies.postValue(Resource.
                <Movies>loading());
        try {
            movies.addSource(mMovieDao.getMovies(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> fetchedMovies) {
                    movies.postValue(Resource.success(new Movies(fetchedMovies)));
                }
            });

        } catch (Exception e) {
            movies.postValue(Resource.<Movies>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return movies;
    }

    @Override
    public LiveData<Resource<Movie>> getMovie(final int movieId) {
        final MediatorLiveData<Resource<Movie>> movie = new MediatorLiveData<>();
        movie.postValue(Resource.
                <Movie>loading());
        try {
            movie.addSource(mMovieDao.getMovie(movieId), new Observer<Movie>() {
                @Override
                public void onChanged(Movie fetchedMovie) {
                    movie.postValue(Resource.success(fetchedMovie));
                }
            });

        } catch (Exception e) {
            movie.postValue(Resource.<Movie>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return movie;
    }

    @Override
    public LiveData<Resource<MovieReviews>> getMovieReviews(int movieId) {
        return null;
    }

    @Override
    public LiveData<Resource<Boolean>> saveToFavorites(final int movieId, final boolean status) {
        final MutableLiveData<Resource<Boolean>> mMovie = new MutableLiveData<>();
        mMovie.postValue(Resource.
                <Boolean>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.saveFavorites(movieId, status);
                    mMovie.postValue(Resource.
                            success(status));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(Resource.<Boolean>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<Resource<Void>> saveMovies(final List<Movie> movies) {
        final MutableLiveData<Resource<Void>> mMovie = new MutableLiveData<>();
        mMovie.postValue(Resource.
                <Void>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.insertAllMovies(movies);
                    mMovie.postValue(Resource.<Void>
                            success(null));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(Resource.<Void>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<Resource<Void>> saveMovie(final Movie movie) {
        final MutableLiveData<Resource<Void>> mMovie = new MutableLiveData<>();
        mMovie.postValue(Resource.
                <Void>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.insertMovie(movie);
                    mMovie.postValue(Resource.<Void>
                            success(null));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(Resource.<Void>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<Resource<Void>> removeMovie(final Movie movie) {
        final MutableLiveData<Resource<Void>> mMovie = new MutableLiveData<>();
        mMovie.postValue(Resource.
                <Void>loading());
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.deleteMovie(movie);
                    mMovie.postValue(Resource.<Void>
                            success(null));
                }
            });
        } catch (Exception e) {
            mMovie.postValue(Resource.<Void>error(new ErrorResponse(GENERIC_ERROR_CODE,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

    @Override
    public LiveData<Resource<MovieTrailers>> getMovieTrailers(int movieId) {
        return null;
    }

}
