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
    private int selectedFilterIndex = 0;


    public MovieViewModel() {
        mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mMovies = getMoviesSortedBy("popularity.desc");
    }

    public LiveData<Resource<Movies>> getMovies() {
        return mMovies;
    }

    public void setMovies(LiveData<Resource<Movies>> movies) {
        mMovies = movies;
    }

    public int getSelectedFilterIndex() {
        return selectedFilterIndex;
    }

    public void setSelectedFilterIndex(int selectedFilterIndex) {
        this.selectedFilterIndex = selectedFilterIndex;
    }

    public LiveData<Resource<Movies>> getMoviesSortedBy(String field) {
        return mMovieRepository.getMovies(field);
    }
}
