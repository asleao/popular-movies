package br.com.popularmovies.services.movieService.source.mappers

import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.services.movieService.response.MovieTable

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

fun MovieDto.toTable(): MovieTable {
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