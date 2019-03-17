package br.com.popularmovies.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.data.source.MovieRepository;
import br.com.popularmovies.movies.data.source.remote.MovieRemoteDataSource;

public class MovieViewModel extends ViewModel {
    private LiveData<Resource<Movies>> mMovies;
    private MovieRepository mMovieRepository;


    public MovieViewModel() {
        mMovies = new MutableLiveData<>();
        mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mMovies = mMovieRepository.getMovies("popularity.desc");
    }

    public LiveData<Resource<Movies>> getMovies() {
        return mMovies;
    }
}
