package br.com.popularmovies.domain.usecases.movies.trailers

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.model.movie.MovieTrailer
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.usecases.UseCase
import javax.inject.Inject

class GetMovieTrailersUseCase @Inject constructor(
    private val movieRepository: br.com.popularmovies.core.data.api.MovieRepository
) : UseCase<GetMovieTrailersUseCase.Params, List<MovieTrailer>>() {

    override suspend fun build(param: Params): Result<List<MovieTrailer>> {
        return movieRepository.getMovieTrailers(param.movieId)
    }

    data class Params(
        val movieId: Long
    )
}