package br.com.popularmovies.movies.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import br.com.popularmovies.data.model.Resource;
import br.com.popularmovies.services.movieService.response.Movies;
import br.com.popularmovies.services.movieService.source.MovieRepository;
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource;

import static br.com.popularmovies.movies.Constants.FILTER_HIGHEST_RATED;
import static br.com.popularmovies.movies.Constants.FILTER_MOST_POPULAR;
import static br.com.popularmovies.movies.Constants.INDEX_FILTER_MOST_POPULAR;

public class MovieViewModel extends ViewModel {
    private LiveData<Resource<Movies>> mMovies;
    private MutableLiveData<String> mSortBy;
    private MovieRepository mMovieRepository;
    private int selectedFilterIndex = 0;


    public MovieViewModel() {
        mMovieRepository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance());
        mSortBy = new MutableLiveData<>();
        mMovies = Transformations.switchMap(mSortBy, new Function<String, LiveData<Resource<Movies>>>() {
            @Override
            public LiveData<Resource<Movies>> apply(String input) {
                return getMoviesSortedBy(input);
            }
        });
        setMovieSortBy(FILTER_MOST_POPULAR);
    }

    public LiveData<Resource<Movies>> getMovies() {
        return mMovies;
    }

    public void setMovies(LiveData<Resource<Movies>> movies) {
        mMovies = movies;
    }

    public void setMovieSortBy(String sortBy) {
        mSortBy.setValue(sortBy);
    }


    public int getSelectedFilterIndex() {
        return selectedFilterIndex;
    }

    public void setSelectedFilterIndex(int selectedFilterIndex) {
        this.selectedFilterIndex = selectedFilterIndex;
    }

    private LiveData<Resource<Movies>> getMoviesSortedBy(String field) {
        return mMovieRepository.getMovies(field);
    }

    public void tryAgain() {
        if (selectedFilterIndex == INDEX_FILTER_MOST_POPULAR) {
            setMovieSortBy(FILTER_MOST_POPULAR);
        } else {
            setMovieSortBy(FILTER_HIGHEST_RATED);
        }
    }
}
