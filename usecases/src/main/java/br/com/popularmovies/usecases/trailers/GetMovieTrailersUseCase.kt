package br.com.popularmovies.usecases.trailers

import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.entities.repository.MovieRepository
import br.com.popularmovies.entities.usecase.UseCase
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieTrailersUseCase.Params, List<MovieTrailer>>() {

    override suspend fun build(param: Params): List<MovieTrailer> {
        return movieRepository.getMovieTrailers(param.movie)
    }

    data class Params(
        val movie: Movie
    )
}