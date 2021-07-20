package br.com.popularmovies.usecases.movies

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieOrderType
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.UseCase
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMoviesUseCase.Params, List<Movie>>() {

    override suspend fun build(param: Params): List<Movie> {
        return movieRepository.getMovies(param.movieOrderType)
    }

    data class Params(
        val movieOrderType: MovieOrderType
    )
}