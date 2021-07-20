package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.*
import br.com.popularmovies.common.models.base.Error
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieOrderType
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.usecases.movies.GetMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _movies: MediatorLiveData<List<Movie>> = MediatorLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val mSortBy: MutableLiveData<MovieOrderType> = MutableLiveData()
    var selectedFilterIndex = 0

    fun setMovieOrder(movieOrderType: MovieOrderType) {
        mSortBy.postValue(movieOrderType)
    }

    private fun getMoviesSortedBy(movieOrderType: MovieOrderType) {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMoviesUseCase.Params(movieOrderType)
            when (val result = getMoviesUseCase.build(params)) {
                is Result.Success -> _movies.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun tryAgain() {
        if (selectedFilterIndex == Constants.INDEX_FILTER_MOST_POPULAR) {
            getMoviesSortedBy(MovieOrderType.MostPopular)
        } else {
            getMoviesSortedBy(MovieOrderType.TopHated)
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
        getMoviesSortedBy(MovieOrderType.MostPopular)
    }
}