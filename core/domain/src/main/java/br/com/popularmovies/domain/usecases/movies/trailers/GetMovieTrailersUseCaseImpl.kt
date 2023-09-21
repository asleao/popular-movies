package br.com.popularmovies.domain.usecases.movies.trailers

import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieTrailersUseCaseParams
import br.com.popularmovies.model.movie.MovieTrailer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieTrailersUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieTrailersUseCase {

    override fun build(param: GetMovieTrailersUseCaseParams): Flow<List<MovieTrailer>> {
        return movieRepository.getMovieTrailers(param.movieId)
    }
}