package br.com.popularmovies.services.movieService.source.mappers

import br.com.popularmovies.datasourceremote.models.movie.MovieTrailerDto
import br.com.popularmovies.entities.movie.MovieTrailer

fun MovieTrailerDto.toDomain(): MovieTrailer {
    return MovieTrailer(
            id = id,
            iso6391 = iso6391,
            iso31661 = iso31661,
            key = key,
            name = name,
            site = site,
            size = size,
            type = type
    )
}
