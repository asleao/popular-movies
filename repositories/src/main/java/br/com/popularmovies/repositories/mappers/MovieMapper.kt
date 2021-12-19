package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.entities.movie.Movie

fun MovieTable.toDomain(): Movie {
    return Movie(
        votes = votes,
        id = id,
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
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDto.toTable(type: MovieTypeTable): MovieTable {
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

fun Movie.toTable(type: MovieTypeTable): MovieTable {
    return MovieTable(
        votes = votes,
        id = id,
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