package br.com.popularmovies.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.usecases.movies.GetInTheaterMoviesUseCase
import br.com.popularmovies.usecases.movies.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(
    private val getInTheaterMoviesUseCase: GetInTheaterMoviesUseCase,
    getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error

    private val _inTheaterMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    val inTheaterMovies: LiveData<List<Movie>>
        get() = _inTheaterMovies

    val popularMoviesFlow: Flow<PagingData<Movie>> =
        getPopularMoviesUseCase.build(Unit).cachedIn(viewModelScope)

    init {
        getInTheaterMovies()
    }

    private fun getInTheaterMovies() = viewModelScope.launch {
        when (val result = getInTheaterMoviesUseCase.build(Unit)) {
            is Result.Success -> _inTheaterMovies.value = result.data
            is Result.Error -> {
                _error.value = result.error
            }
        }
    }

}