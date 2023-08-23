package br.com.popularmovies.data.mappers

import br.com.popularmovies.core.api.models.movie.MovieTable
import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieDto
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType

fun MovieTable.toDomain(): Movie {
    return Movie(
        votes = votes,
        id = remoteId,
        type = type.toDomain(),
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDto.toDomain(): Movie {
    return Movie(
        votes = votes,
        id = id,
        type = MovieType.Unknown,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDto.toTable(type: MovieTypeTable = MovieTypeTable.Unknown): MovieTable {
    return MovieTable(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite,
        type = type
    )
}