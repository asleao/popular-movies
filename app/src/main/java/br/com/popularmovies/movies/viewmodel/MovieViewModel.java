package br.com.popularmovies.movies.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.movies.data.response.Movies;
import br.com.popularmovies.movies.data.source.MovieRepository;
import br.com.popularmovies.movies.data.source.remote.MovieRemoteDataSource;

public class MovieViewModel extends ViewModel {
    private LiveData<Resource<Movies>> mMovies;
    private MovieRepository mMovieRepository;


    public MovieViewModel() {
        mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mMovies = getMoviesSortedBy("popularity.desc");
    }

    public LiveData<Resource<Movies>> getMovies() {
        return mMovies;
    }

    public LiveData<Resource<Movies>> getMoviesSortedBy(String field) {
        return mMovieRepository.getMovies(field);
    }
}
