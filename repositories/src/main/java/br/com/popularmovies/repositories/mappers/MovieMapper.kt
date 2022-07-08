package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourcedb.models.movie.MovieTable
import br.com.popularmovies.datasourceremote.models.movie.MovieDto
import br.com.popularmovies.entities.movie.Movie

fun MovieTable.toDomain(): Movie {
    return Movie(
        votes = votes,
        id = remoteId,
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

fun MovieDto.toMostPopularTable(): MovieTable.MostPopular {
    return MovieTable.MostPopular(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDto.toTopRatedTable(): MovieTable.TopRated {
    return MovieTable.TopRated(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun MovieDto.toNowPlayingTable(): MovieTable.NowPlaying {
    return MovieTable.NowPlaying(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun Movie.toMostPopularTable(): MovieTable.MostPopular {
    return MovieTable.MostPopular(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun Movie.toTopRatedTable(): MovieTable.TopRated {
    return MovieTable.TopRated(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}

fun Movie.toNowPlayingTable(): MovieTable.NowPlaying {
    return MovieTable.NowPlaying(
        votes = votes,
        remoteId = id,
        voteAverage = voteAverage,
        originalTitle = originalTitle,
        popularity = popularity,
        poster = poster,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite
    )
}