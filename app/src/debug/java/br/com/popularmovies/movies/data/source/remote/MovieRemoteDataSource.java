package br.com.popularmovies.movies.data.source.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import br.com.popularmovies.data.ServiceGenerator;
import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.data.service.MovieService;
import br.com.popularmovies.movies.data.source.MovieDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRemoteDataSource implements MovieDataSource {
    private volatile static MovieRemoteDataSource INSTANCE = null;
    private MovieService mMovieService;

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
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Movies> call, @NotNull Throwable t) {
                Log.e("getMovies", t.getMessage());
            }
        });
        return movies;
    }
}
