package br.com.popularmovies.movies.data.source.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import br.com.popularmovies.data.ServiceGenerator;
import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.data.service.MovieService;
import br.com.popularmovies.movies.data.source.MovieDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.popularmovies.movies.Constants.CONNECTION_MSG_ERROR;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_MESSAGE;
import static br.com.popularmovies.movies.Constants.SERVER_MSG_ERROR;

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
        final MutableLiveData<Resource<Movies>> movies = new MutableLiveData<>();
        movies.setValue(Resource.<Movies>loading());
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NotNull Call<Movies> call, @NotNull Response<Movies> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        movies.setValue(Resource.success(response.body()));
                    } else {
                        if (response.errorBody() != null) {
                            ErrorResponse error = null;
                            if (response.code() >= 400 && response.code() < 500) {
                                Moshi moshi = new Moshi.Builder().build();
                                JsonAdapter<ErrorResponse> jsonAdapter = moshi.adapter(ErrorResponse.class);
                                try {
                                    error = jsonAdapter.fromJson(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                error = new ErrorResponse(response.code(),
                                        SERVER_MSG_ERROR);
                            }
                            movies.setValue(Resource.<Movies>error(error));

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movies> call, @NotNull Throwable t) {
                ErrorResponse error;
                if (t instanceof IOException) {
                    error = new ErrorResponse(503,
                            CONNECTION_MSG_ERROR);
                } else {
                    error = new ErrorResponse(500,
                            GENERIC_MSG_ERROR_MESSAGE);
                }
                movies.setValue(Resource.<Movies>error(error));
                Log.e(GET_MOVIES_TAG, t.getMessage());
            }
        });
        return movies;
    }
}
