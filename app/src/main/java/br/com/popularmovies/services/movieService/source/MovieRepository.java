package br.com.popularmovies.services.movieService.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;

import static br.com.popularmovies.movies.Constants.CONNECTION_MSG_ERROR;
import static br.com.popularmovies.utils.NetworkUtils.isOnline;

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
    public LiveData<Resource<Movie>> getMovie(int movieId) {
        final MediatorLiveData<Resource<Movie>> movie = new MediatorLiveData<>();
        final LiveData<Resource<Movie>> dbSource = mMovieLocalDataSource.getMovie(movieId);
        final LiveData<Resource<Movie>> networkSource = mMovieRemoteDataSource.getMovie(movieId);
        movie.addSource(dbSource, new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(Resource<Movie> resource) {
                switch (resource.status) {
                    case SUCCESS:
                        if (resource.data == null) {
                            movie.removeSource(dbSource);
                            movie.addSource(networkSource, new Observer<Resource<Movie>>() {
                                @Override
                                public void onChanged(Resource<Movie> resource) {
                                    movie.postValue(resource);
                                }
                            });
                        } else {
                            movie.postValue(resource);
                        }
                        break;
                    case ERROR:
                        movie.postValue(resource);
                        break;
                }
            }
        });
        return movie;
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
