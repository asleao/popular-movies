package br.com.popularmovies.moviedetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.usecases.movies.GetMovieUseCase
import br.com.popularmovies.usecases.movies.favorites.SaveMovieToFavoritesUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val saveMovieToFavoritesUseCase: SaveMovieToFavoritesUseCase,
    @Assisted private val movieId: Int
) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }

    val loading = MutableLiveData<Boolean>()

    private val _error = MutableLiveData<NetworkError>()
    val error: LiveData<NetworkError>
        get() = _error

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _isMovieFavorite = MutableLiveData<Boolean>()
    val isMovieFavorite: LiveData<Boolean>
        get() = _isMovieFavorite

    init {
        getMovie()
    }

    private fun getMovie() {
        viewModelScope.launch {
            showLoading(true)
            val params = GetMovieUseCase.Params(movieId)
            when (val result = getMovieUseCase.build(params)) {
                is Result.Success -> _movie.value = result.data
                is Result.Error -> _error.value = result.error
            }
        }
    }

    fun updateMovie() {
        movie.value?.let { movie ->
            viewModelScope.launch {
                //TODO Refactor
                val params =
                    SaveMovieToFavoritesUseCase.Params(movie.copy(isFavorite = !movie.isFavorite))
                when (val result = saveMovieToFavoritesUseCase.build(params)) {
                    is Result.Success -> {
                        _isMovieFavorite.value = !movie.isFavorite
                        _movie.value = movie.copy(isFavorite = !movie.isFavorite)
                    }
                    is Result.Error -> {
                        _error.value = result.error
                    }
                }
            }
        }
    }

    fun showLoading(value: Boolean) {
        loading.value = value
    }

    fun tryAgain() {
        getMovie()
    }

}