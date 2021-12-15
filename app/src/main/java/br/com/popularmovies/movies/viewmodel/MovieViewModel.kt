package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieOrderType
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.usecases.movies.GetMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val mMovieRepository: MovieRepository
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error

    private val _movies: MediatorLiveData<List<Movie>> = MediatorLiveData()
    val movies: LiveData<List<Movie>>
        get() = _movies

    private val mSortBy: MutableLiveData<MovieOrderType> = MutableLiveData()
    var selectedFilterIndex = 0

    val moviesFlow: Flow<PagingData<Movie>> =
        mMovieRepository.getMovies().cachedIn(viewModelScope)

    init {
//        _movies.addSource(mSortBy) { sortQuery ->
//            getMoviesSortedBy(sortQuery)
//        }
//        getMoviesSortedBy(MovieOrderType.MostPopular)
    }

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
}