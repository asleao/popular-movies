package br.com.popularmovies.data.mappers

import br.com.popularmovies.core.api.models.trailer.TrailerTable
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTrailerDto
import br.com.popularmovies.model.movie.MovieTrailer

fun MovieTrailerDto.toDomain(): MovieTrailer {
    return MovieTrailer(
        id = id,
        key = key,
        name = name
    )
}

fun MovieTrailerDto.toTable(trailerRemoteId: String, movieId: Long): TrailerTable {
    return TrailerTable(
        remoteId = trailerRemoteId,
        movieId = movieId,
        key = key,
        name = name
    )
}

fun TrailerTable.toDomain(): MovieTrailer {
    return MovieTrailer(
        id = remoteId,
        key = key,
        name = name
    )
}
