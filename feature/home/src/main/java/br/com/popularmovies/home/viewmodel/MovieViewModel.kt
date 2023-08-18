package br.com.popularmovies.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.popularmovies.common.models.base.NetworkError
import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase,
    getMoviesUseCaseImpl: GetMoviesUseCase
) : ViewModel() {

    val loading = MutableLiveData(false)

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Success)
    val uiState: StateFlow<MovieUiState> = _uiState

    private val _randomNowPlayingMovie: MutableLiveData<List<Movie>> = MutableLiveData()
    val randomNowPlayingMovie: LiveData<List<Movie>>
        get() = _randomNowPlayingMovie

    val popularMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCaseImpl.build(GetMoviesUseCaseParams(MovieType.MostPopular))
            .cachedIn(viewModelScope)

    val nowPlayingMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCaseImpl.build(GetMoviesUseCaseParams(MovieType.NowPlaying))
            .cachedIn(viewModelScope)

    val topHatedMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCaseImpl.build(GetMoviesUseCaseParams(MovieType.TopRated))
            .cachedIn(viewModelScope)

    init {
        getNewestNowPlayingMovie()
    }

    private fun getNewestNowPlayingMovie() = viewModelScope.launch {
        when (val result = getRandomNowPlayingMovieUseCase.build(Unit)) {
            is Result.Success -> {
                _randomNowPlayingMovie.value = result.data
                _uiState.value = MovieUiState.Success
            }

            is Result.Error -> {
                _uiState.value = MovieUiState.Error(result.error)
            }
        }
    }

    fun tryAgain() {
        //TODO For now, the getNewestNowPlayingMovie controls the visibility. Since the pagingData network error
        // is catch on the LoadState listener. Check a nice approach to deal with that if the screen only has those flows types.
        getNewestNowPlayingMovie()
    }
}


sealed class MovieUiState {
    object Success : MovieUiState()
    data class Error(val networkError: NetworkError?) : MovieUiState()
}