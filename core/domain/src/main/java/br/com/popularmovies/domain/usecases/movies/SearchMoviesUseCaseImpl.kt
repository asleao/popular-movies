package br.com.popularmovies.domain.usecases.movies

import androidx.paging.PagingData
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.SearchMoviesUseCase
import br.com.popularmovies.domain.api.usecases.SearchMoviesUseCaseParams
import br.com.popularmovies.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : SearchMoviesUseCase {

    override fun build(param: SearchMoviesUseCaseParams): Flow<PagingData<Movie>> {
        return movieRepository.searchMovies(param.query)
    }
}