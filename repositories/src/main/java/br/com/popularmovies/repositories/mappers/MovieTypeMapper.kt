package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremote.models.movie.MovieTypeParam
import br.com.popularmovies.entities.movie.MovieType

fun MovieTypeTable.toParam(): MovieTypeParam {
    return when (this) {
        MovieTypeTable.MostPopular -> MovieTypeParam.MostPopular
        MovieTypeTable.TopRated -> MovieTypeParam.TopRated
        MovieTypeTable.NowPlaying -> MovieTypeParam.NowPlaying
        MovieTypeTable.Unknown -> MovieTypeParam.Unknown
    }
}

fun MovieType.toTable(): MovieTypeTable {
    return when (this) {
        MovieType.MostPopular -> MovieTypeTable.MostPopular
        MovieType.TopRated -> MovieTypeTable.TopRated
        MovieType.NowPlaying -> MovieTypeTable.NowPlaying
        MovieType.Unknown -> MovieTypeTable.Unknown
    }
}