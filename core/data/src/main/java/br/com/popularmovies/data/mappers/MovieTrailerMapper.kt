package br.com.popularmovies.data.mappers

import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.model.movie.MovieTrailer

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
