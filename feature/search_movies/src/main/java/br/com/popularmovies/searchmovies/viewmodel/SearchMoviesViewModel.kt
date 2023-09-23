package br.com.popularmovies.searchmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import br.com.popularmovies.domain.api.usecases.SearchMoviesUseCase
import br.com.popularmovies.domain.api.usecases.SearchMoviesUseCaseParams
import br.com.popularmovies.model.movie.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SearchMoviesViewModel @Inject constructor(
    searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {
    val _query = MutableStateFlow("")
    val query: StateFlow<String>
        get() = _query

    val _isSearchVisible = MutableStateFlow(false)
    val isSearchVisible: StateFlow<Boolean>
        get() = _isSearchVisible

    val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<PagingData<Movie>> = query
        .debounce(500L)
        .onEach { _isSearchVisible.update { true } }
        .flatMapLatest { query ->
            searchMoviesUseCase.build(SearchMoviesUseCaseParams(query))
        }
        .onEach { _isSearchVisible.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PagingData.empty()
        )


    fun onQueryChanged(value: String) {
        _query.value = value
    }

    private fun validateQuery(query: String, movie: Movie): Boolean {
        with(movie) {
            val matchingCombinations = listOf(
                movie.originalTitle,
            )
            return matchingCombinations.any {
                it.contains(query, ignoreCase = true)
            }
        }
    }
}