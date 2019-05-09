package br.com.popularmovies.services.movieService.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import br.com.popularmovies.data.ServiceGenerator;
import br.com.popularmovies.data.model.Resource;
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
}