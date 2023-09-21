package br.com.popularmovies.moviedetails.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.popularmovies.domain.api.usecases.GetMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieFavoriteUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieReviewsUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCaseParams
import br.com.popularmovies.domain.api.usecases.GetMovieUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieUseCaseParams
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.UpdateMovieFavoriteUseCaseParams
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieReview
import br.com.popularmovies.model.movie.MovieTrailer
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    getMovieUseCase: GetMovieUseCase,
    getMovieFavoriteUseCase: GetMovieFavoriteUseCase,
    getMovieTrailersUseCase: GetMovieTrailersUseCase,
    getMovieReviewsUseCase: GetMovieReviewsUseCase,
    private val updateMovieFavoriteUseCase: UpdateMovieFavoriteUseCase,
    @Assisted private val movieId: Long
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Long): MovieDetailViewModel
    }

    // https://github.com/Kotlin/kotlinx.coroutines/issues/3594
    private val isRefresh =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .apply { tryEmit(false) }

    var uiState by mutableStateOf<MovieDetailUiState>(MovieDetailUiState.Loading)
        private set


    init {
        viewModelScope.launch {
            isRefresh
                .flatMapLatest {
                    combine(
                        getMovieUseCase.build(GetMovieUseCaseParams(movieId)),
                        getMovieFavoriteUseCase.build(GetMovieFavoriteUseCaseParams(movieId)),
                        getMovieTrailersUseCase.build(GetMovieTrailersUseCaseParams(movieId)),
                        getMovieReviewsUseCase.build(GetMovieReviewsUseCaseParams(movieId))
                    ) { movie, movieFavorite, reviews, trailers ->
                        uiState =
                            MovieDetailUiState.Success(
                                movie,
                                movieFavorite.isFavorite,
                                trailers,
                                reviews
                            )
                    }.catch {
                        uiState = MovieDetailUiState.Error
                    }
                        .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(5_000),
                            initialValue = MovieDetailUiState.Loading
                        )
                }.collect()
        }
    }

    fun tryAgain() {
        isRefresh.tryEmit(true)
    }

    fun saveToFavorites() {
        viewModelScope.launch {
            val state = uiState as? MovieDetailUiState.Success
            val movie = state?.movie ?: return@launch
            try {
                updateMovieFavoriteUseCase.build(
                    UpdateMovieFavoriteUseCaseParams(
                        movie,
                        !state.isMovieFavorite
                    )
                )
            } catch (_: Exception) {
                uiState = MovieDetailUiState.Error
            }
        }
    }
}

sealed interface MovieDetailUiState {
    object Loading : MovieDetailUiState
    object Error : MovieDetailUiState

    data class Success(
        val movie: Movie,
        val isMovieFavorite: Boolean,
        val reviews: List<MovieReview>,
        val trailers: List<MovieTrailer>
    ) : MovieDetailUiState
}