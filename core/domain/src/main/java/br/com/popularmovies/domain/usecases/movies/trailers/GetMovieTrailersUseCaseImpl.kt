package br.com.popularmovies.domain.usecases.movies.trailers

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCaseParams
import br.com.popularmovies.model.movie.MovieTrailer
import javax.inject.Inject

class GetMovieTrailersUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieTrailersUseCase {

    override suspend fun build(param: GetMovieTrailersUseCaseParams): Result<List<MovieTrailer>> {
        return movieRepository.getMovieTrailers(param.movieId)
    }

}