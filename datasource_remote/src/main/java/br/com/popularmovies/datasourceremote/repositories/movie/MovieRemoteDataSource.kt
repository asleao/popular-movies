package br.com.popularmovies.datasourceremote.repositories.movie

import br.com.popularmovies.common.models.base.Result
import br.com.popularmovies.datasourceremote.models.base.RetrofitResponse
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.datasourceremote.models.movie.MovieReviewDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.datasourceremote.models.movie.MovieTypeParam
import br.com.popularmovies.datasourceremote.services.movie.MovieService
import br.com.popularmovies.datasourceremote.utils.mapApiResult
import br.com.popularmovies.datasourceremote.utils.mapApiResults
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    retrofit: Retrofit,
    private val retrofitResponse: RetrofitResponse
) {
    private val mMovieService: MovieService = retrofit.create(MovieService::class.java)

    suspend fun getMovies(page: Int, type: MovieTypeParam): Result<List<MovieDto>> {
        return retrofitResponse
            .request {
                when (type) {
                    MovieTypeParam.TopRated -> mMovieService.getTopRatedMovies(page)
                    MovieTypeParam.MostPopular -> mMovieService.getPopularMovies(page)
                    MovieTypeParam.NowPlaying -> mMovieService.getNowPlayingMovies(page)
                }
            }
            .mapApiResults()
    }

    suspend fun getMovie(movieId: Long): Result<MovieDto> {
        return retrofitResponse
            .request { mMovieService.getMovie(movieId) }
            .mapApiResult()
    }

    suspend fun getMovieReviews(movieId: Long): Result<List<MovieReviewDto>> {
        return retrofitResponse
            .request { mMovieService.getMovieReviews(movieId) }
            .mapApiResults()
    }

    suspend fun getMovieTrailers(movieId: Long): Result<List<MovieTrailerDto>> {
        return retrofitResponse
            .request { mMovieService.getMovieTrailers(movieId) }
            .mapApiResults()
    }

    suspend fun getNowPlayingMovies(page: Int): Result<List<MovieDto>> {
        return retrofitResponse
            .request { mMovieService.getNowPlayingMovies(page) }
            .mapApiResults()
    }
}