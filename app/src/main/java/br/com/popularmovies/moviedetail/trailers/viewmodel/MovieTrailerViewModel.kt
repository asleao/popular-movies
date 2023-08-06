package br.com.popularmovies.moviedetail.trailers.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.MovieTrailer
import br.com.popularmovies.usecases.movies.trailers.GetMovieTrailersUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieTrailerViewModel @AssistedInject constructor(
    private val getMovieTrailersUseCase: GetMovieTrailersUseCase,
    @Assisted val movieId: Long
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Long): MovieTrailerViewModel
    }

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error

    private val _trailers = MutableLiveData<List<MovieTrailer>>()
    val trailers: LiveData<List<MovieTrailer>> = _trailers

    init {
        getTrailers()
    }

    private fun getTrailers() {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMovieTrailersUseCase.Params(movieId)
            when (val result =
                getMovieTrailersUseCase.build(params)) {
                is Result.Success -> _trailers.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getTrailers()
    }
}