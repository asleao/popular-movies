package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.popularmovies.data.model.OldResource
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.source.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val mMovieRepository: MovieRepository) : ViewModel() {
    val movies: LiveData<OldResource<Movies>>
    private val mSortBy: MutableLiveData<String> = MutableLiveData()
    var selectedFilterIndex = 0

    fun setMovieSortBy(sortBy: String) {
        mSortBy.postValue(sortBy)
    }

    private fun getMoviesSortedBy(field: String): LiveData<OldResource<Movies>> {
        return mMovieRepository.getMovies(field)
    }

    fun tryAgain() {
        if (selectedFilterIndex == Constants.INDEX_FILTER_MOST_POPULAR) {
            setMovieSortBy(Constants.FILTER_MOST_POPULAR)
        } else {
            setMovieSortBy(Constants.FILTER_HIGHEST_RATED)
        }
    }

    init {
        movies = Transformations.switchMap(mSortBy) { input -> getMoviesSortedBy(input) }
        setMovieSortBy(Constants.FILTER_MOST_POPULAR)
    }
}