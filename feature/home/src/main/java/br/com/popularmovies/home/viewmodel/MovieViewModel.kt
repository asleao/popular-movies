package br.com.popularmovies.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCase
import br.com.popularmovies.domain.api.usecases.GetMoviesUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetRandomNowPlayingMovieUseCase
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase,
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val isRefresh =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .apply { tryEmit(false) }

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState

    private val _randomNowPlayingMovie = MutableStateFlow<List<Movie>>(emptyList())
    val randomNowPlayingMovie: Flow<List<Movie>> = _randomNowPlayingMovie

    private val _popularMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val popularMoviesFlow: Flow<PagingData<Movie>> = _popularMovies
        .cachedIn(viewModelScope)


    private val _nowPlayingMoviesFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val nowPlayingMoviesFlow: Flow<PagingData<Movie>> =
        _nowPlayingMoviesFlow
            .cachedIn(viewModelScope)

    private val _topHatedMoviesFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val topHatedMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.TopRated))
            .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            isRefresh
                .flatMapLatest {
                    getRandomNowPlayingMovieUseCase.build(Unit)
                        .onStart { _uiState.value = MovieUiState.Loading }
                        .catch { exception ->
                            _uiState.value = MovieUiState.Error(exception)
                        }
                        .collectLatest {
                            _randomNowPlayingMovie.value = it
                        }

                    combine(
                        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.MostPopular)),
                        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.NowPlaying)),
                        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.TopRated))
                    ) { mostPopularMovies, nowPlayingMovies, topRatedMovies ->
                        _popularMovies.value = mostPopularMovies
                        _nowPlayingMoviesFlow.value = nowPlayingMovies
                        _topHatedMoviesFlow.value = topRatedMovies
                        _uiState.value = MovieUiState.Success
                    }.distinctUntilChanged()
                        .onStart { _uiState.value = MovieUiState.Loading }
                        .catch { exception ->
                            _uiState.value = MovieUiState.Error(exception)
                        }.stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(5_000),
                            initialValue = MovieUiState.Loading
                        )
                }.collect()
        }
    }

    fun showError(exception: Throwable?) {
        _uiState.value = MovieUiState.Error(exception)
    }

    fun tryAgain() {
        isRefresh.tryEmit(true)
    }
}


sealed interface MovieUiState {
    object Loading : MovieUiState
    object Success : MovieUiState
    data class Error(val networkError: Throwable?) : MovieUiState
}