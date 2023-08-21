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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val getRandomNowPlayingMovieUseCase: GetRandomNowPlayingMovieUseCase,
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Success)
    val uiState: StateFlow<MovieUiState> = _uiState

    val randomNowPlayingMovie: Flow<List<Movie>> = getRandomNowPlayingMovieUseCase.build(Unit)

    val popularMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.MostPopular))
            .cachedIn(viewModelScope)

    val nowPlayingMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.NowPlaying))
            .cachedIn(viewModelScope)

    val topHatedMoviesFlow: Flow<PagingData<Movie>> =
        getMoviesUseCase.build(GetMoviesUseCaseParams(MovieType.TopRated))
            .cachedIn(viewModelScope)

    fun tryAgain() {
        //TODO For now, the getNewestNowPlayingMovie controls the visibility. Since the pagingData network error
        // is catch on the LoadState listener. Check a nice approach to deal with that if the screen only has those flows types.
//        getNewestNowPlayingMovie()
    }
}


sealed class MovieUiState {
    object Success : MovieUiState()
    data class Error(val networkError: Throwable?) : MovieUiState()
}