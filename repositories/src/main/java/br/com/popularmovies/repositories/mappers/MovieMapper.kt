package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourcedb.models.movie.MovieTable
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

fun MovieDto.toTable(id: Long): MovieTable {
    return MovieTable(
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

fun Movie.toTable(): MovieTable {
    return MovieTable(
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

fun Movie.toDto(): MovieDto {
    return MovieDto(
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