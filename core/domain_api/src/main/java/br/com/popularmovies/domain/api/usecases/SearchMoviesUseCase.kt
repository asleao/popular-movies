package br.com.popularmovies.domain.api.usecases

import androidx.paging.PagingData
import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.model.movie.Movie

interface SearchMoviesUseCase : FlowUseCase<SearchMoviesUseCaseParams, PagingData<Movie>>
data class SearchMoviesUseCaseParams(
    val query: String
)