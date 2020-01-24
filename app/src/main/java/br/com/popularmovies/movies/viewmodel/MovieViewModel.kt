package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.*
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.services.movieService.response.Movies
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.utils.validateResponse
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(private val mMovieRepository: MovieRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _movies: MediatorLiveData<Movies> = MediatorLiveData()
    val movies: LiveData<Movies>
        get() = _movies

    private val mSortBy: MutableLiveData<String> = MutableLiveData()
    var selectedFilterIndex = 0

    fun setMovieSortBy(sortBy: String) {
        mSortBy.postValue(sortBy)
    }

    private fun getMoviesSortedBy(field: String) {
        viewModelScope.launch {
            showLoading(true)
            val resource = mMovieRepository.getMovies(field)
            resource.validateResponse(_movies, _error)
        }
    }

    fun tryAgain() {
        if (selectedFilterIndex == Constants.INDEX_FILTER_MOST_POPULAR) {
            getMoviesSortedBy(Constants.FILTER_MOST_POPULAR)
        } else {
            getMoviesSortedBy(Constants.FILTER_HIGHEST_RATED)
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    init {
        _movies.addSource(mSortBy) { sortQuery ->
            getMoviesSortedBy(sortQuery)
        }
        getMoviesSortedBy(Constants.FILTER_MOST_POPULAR)
    }
}