package br.com.popularmovies.services.movieService.source.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import br.com.popularmovies.data.local.AppDatabase;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;
import br.com.popularmovies.services.movieService.source.MovieDataSource;
import br.com.popularmovies.utils.AppExecutors;

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

    @Override
    public LiveData<Resource<Movies>> getMovies(final String orderBy) {
        final MutableLiveData<Resource<Movies>> movies = new MutableLiveData<>();

        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    movies.setValue(Resource.
                            success(new Movies(mMovieDao.getMovies(orderBy).getValue())));
                }
            });
        } catch (Exception e) {
            movies.setValue(Resource.<Movies>error(new ErrorResponse(500,
                    ROOM_MSG_ERROR)));
        }
        return movies;
    }

    @Override
    public LiveData<Resource<MovieReviews>> getMovieReviews(int movieId) {
        return null;
    }

    @Override
    public LiveData<Resource<Boolean>> saveMovie(final Movie movie) {
        final MutableLiveData<Resource<Boolean>> mMovie = new MutableLiveData<>();
        try {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieDao.updateMovie(movie);
                    mMovie.setValue(Resource.
                            success(true));
                }
            });
        } catch (Exception e) {
            mMovie.setValue(Resource.<Boolean>error(new ErrorResponse(500,
                    ROOM_MSG_ERROR)));
        }
        return mMovie;
    }

}
