package br.com.popularmovies.usecases.movies

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.UseCase
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieUseCase.Params, Movie>() {

    override suspend fun build(param: Params): Movie {
        return movieRepository.getMovie(param.movieId)
    }

    data class Params(
        val movieId: Int
    )
}