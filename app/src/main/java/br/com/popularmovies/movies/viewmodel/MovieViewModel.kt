package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.*
import br.com.popularmovies.datanetwork.models.base.Error
import br.com.popularmovies.datanetwork.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.services.movieService.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(private val mMovieRepository: MovieRepository) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _movies: MediatorLiveData<List<Movie>> = MediatorLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val mSortBy: MutableLiveData<String> = MutableLiveData()
    var selectedFilterIndex = 0

    fun setMovieSortBy(sortBy: String) {
        mSortBy.postValue(sortBy)
    }

    private fun getMoviesSortedBy(field: String) {
        viewModelScope.launch {
            showLoading(true)
            when (val result = mMovieRepository.getMovies(field)) {
                is Result.Success -> _movies.value = result.data
                is Result.Error -> _error.value = result.error
            }
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

    fun cleanError() {
        _error.value = null
    }

    init {
        _movies.addSource(mSortBy) { sortQuery ->
            getMoviesSortedBy(sortQuery)
        }
        getMoviesSortedBy(Constants.FILTER_MOST_POPULAR)
    }
}