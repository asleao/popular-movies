package br.com.popularmovies.services.movieService.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.popularmovies.data.ServiceGenerator;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movie;
import br.com.popularmovies.services.movieService.response.MovieReviews;
import br.com.popularmovies.services.movieService.response.Movies;
import br.com.popularmovies.services.movieService.service.MovieService;
import br.com.popularmovies.services.movieService.source.ApiResponse;
import br.com.popularmovies.services.movieService.source.MovieDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRemoteDataSource implements MovieDataSource {
    private volatile static MovieRemoteDataSource INSTANCE = null;
    private MovieService mMovieService;
    private final String GET_MOVIES_TAG = "getMovies";


    private MovieRemoteDataSource() {
        mMovieService = ServiceGenerator.createService(MovieService.class);
    }

    public static MovieRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (MovieRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public LiveData<Resource<Movies>> getMovies(String orderBy) {
        Call<Movies> call = mMovieService.getMovies(orderBy);
        final ApiResponse<Movies> apiResponse = new ApiResponse<>(GET_MOVIES_TAG);
        final MutableLiveData<Resource<Movies>> movies = new MutableLiveData<>();
        movies.setValue(Resource.<Movies>loading());
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NotNull Call<Movies> call, @NotNull Response<Movies> response) {
                movies.setValue(apiResponse.getApiOnResponse(response));
            }

            @Override
            public void onFailure(@NotNull Call<Movies> call, @NotNull Throwable t) {
                movies.setValue(apiResponse.getApiOnFailure(t));
            }
        });
        return movies;
    }

    @Override
    public LiveData<Resource<Movie>> getMovie(int movieId) {
        Call<Movie> call = mMovieService.getMovie(movieId);
        final ApiResponse<Movie> apiResponse = new ApiResponse<>(GET_MOVIES_TAG);
        final MutableLiveData<Resource<Movie>> movie = new MutableLiveData<>();
        movie.setValue(Resource.<Movie>loading());
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                movie.setValue(apiResponse.getApiOnResponse(response));
            }

            @Override
            public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {
                movie.setValue(apiResponse.getApiOnFailure(t));
            }
        });
        return movie;
    }

    @Override
    public LiveData<Resource<MovieReviews>> getMovieReviews(int movieId) {
        Call<MovieReviews> call = mMovieService.getMovieReviews(movieId);
        final ApiResponse<MovieReviews> apiResponse = new ApiResponse<>(GET_MOVIES_TAG);
        final MutableLiveData<Resource<MovieReviews>> reviews = new MutableLiveData<>();
        reviews.setValue(Resource.<MovieReviews>loading());
        call.enqueue(new Callback<MovieReviews>() {
            @Override
            public void onResponse(@NotNull Call<MovieReviews> call, @NotNull Response<MovieReviews> response) {
                reviews.setValue(apiResponse.getApiOnResponse(response));
            }

            @Override
            public void onFailure(@NotNull Call<MovieReviews> call, @NotNull Throwable t) {
                reviews.setValue(apiResponse.getApiOnFailure(t));
            }
        });
        return reviews;
    }

    @Override
    public LiveData<Resource<Boolean>> saveToFavorites(int movieId, boolean status) {
        return new MutableLiveData();
    }

    @Override
    public LiveData<Resource<Void>> saveMovies(List<Movie> movies) {
        return null;
    }

    @Override
    public LiveData<Resource<Void>> saveMovie(Movie movie) {
        return null;
    }

    @Override
    public LiveData<Resource<Void>> removeMovie(Movie movie) {
        return null;
    }
}
