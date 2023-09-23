package br.com.popularmovies.data.mappers

import br.com.popularmovies.core.api.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremoteapi.models.movie.MovieTypeParam
import br.com.popularmovies.model.movie.MovieType

fun MovieTypeTable.toParam(): MovieTypeParam {
    return when (this) {
        MovieTypeTable.MostPopular -> MovieTypeParam.MostPopular
        MovieTypeTable.TopRated -> MovieTypeParam.TopRated
        MovieTypeTable.NowPlaying -> MovieTypeParam.NowPlaying
        MovieTypeTable.Search -> MovieTypeParam.Search
        MovieTypeTable.Unknown -> MovieTypeParam.Unknown
    }
}

fun MovieType.toParam(): MovieTypeParam {
    return when (this) {
        MovieType.MostPopular -> MovieTypeParam.MostPopular
        MovieType.TopRated -> MovieTypeParam.TopRated
        MovieType.NowPlaying -> MovieTypeParam.NowPlaying
        MovieType.Search -> MovieTypeParam.Search
        MovieType.Unknown -> MovieTypeParam.Unknown
    }
}

fun MovieType.toTable(): MovieTypeTable {
    return when (this) {
        MovieType.MostPopular -> MovieTypeTable.MostPopular
        MovieType.TopRated -> MovieTypeTable.TopRated
        MovieType.NowPlaying -> MovieTypeTable.NowPlaying
        MovieType.Search -> MovieTypeTable.Search
        MovieType.Unknown -> MovieTypeTable.Unknown
    }
}

fun MovieTypeTable.toDomain(): MovieType {
    return when (this) {
        MovieTypeTable.MostPopular -> MovieType.MostPopular
        MovieTypeTable.TopRated -> MovieType.TopRated
        MovieTypeTable.NowPlaying -> MovieType.NowPlaying
        MovieTypeTable.Search -> MovieType.Search
        MovieTypeTable.Unknown -> MovieType.Unknown
    }
}