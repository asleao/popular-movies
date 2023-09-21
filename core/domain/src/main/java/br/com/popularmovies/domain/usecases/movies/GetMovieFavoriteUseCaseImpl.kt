package br.com.popularmovies.domain.usecases.movies

import br.com.popularmovies.core.data.api.MovieRepository
import br.com.popularmovies.domain.api.usecases.GetMovieFavoriteUseCase
import br.com.popularmovies.domain.api.usecases.GetMovieFavoriteUseCaseParams
import br.com.popularmovies.model.movie.MovieFavorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieFavoriteUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieFavoriteUseCase {
    override fun build(param: GetMovieFavoriteUseCaseParams): Flow<MovieFavorite> {
        return movieRepository.getMovieFavorite(param.movieId)
    }

}