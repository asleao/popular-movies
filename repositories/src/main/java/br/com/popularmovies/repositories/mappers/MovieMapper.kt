package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.entities.movie.MovieType

fun MovieTable.toDomain(type: MovieType): Movie {
    return Movie(
        votes = votes,
        id = remoteId,
        type = type,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDto.toDomain(type: MovieType = MovieType.Unknown): Movie {
    return Movie(
        votes = votes,
        id = id,
        type = type,
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