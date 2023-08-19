package br.com.popularmovies.domain.api.usecases

import androidx.paging.PagingData
import br.com.popularmovies.domain.api.usecases.base.FlowUseCase
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType

interface GetMoviesUseCase : FlowUseCase<GetMoviesUseCaseParams, PagingData<Movie>>
data class GetMoviesUseCaseParams(
    val movieType: MovieType
)