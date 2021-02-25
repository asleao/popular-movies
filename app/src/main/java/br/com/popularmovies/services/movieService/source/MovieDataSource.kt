package br.com.popularmovies.services.movieService.source

import br.com.popularmovies.core.network.retrofit.model.Resource
import br.com.popularmovies.services.movieService.response.MovieDto
import br.com.popularmovies.services.movieService.response.MovieReviews
import br.com.popularmovies.services.movieService.response.MovieTrailers
import br.com.popularmovies.services.movieService.response.Movies

interface MovieDataSource {

    suspend fun getMovies(orderBy: String): Resource<Movies>

    suspend fun getMovie(movieId: Int): Resource<MovieDto>

    suspend fun getMovieReviews(movieId: Int): Resource<MovieReviews>

    suspend fun saveToFavorites(movieDto: MovieDto): Resource<Unit>

    suspend fun getMovieTrailers(movieId: Int): Resource<MovieTrailers>
}
