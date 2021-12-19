package br.com.popularmovies.repositories.mappers

import br.com.popularmovies.datasourcedb.models.movie.MovieTypeTable
import br.com.popularmovies.datasourceremote.models.movie.MovieTypeParam

fun MovieTypeTable.toParam(): MovieTypeParam {
    return when (this) {
        MovieTypeTable.MostPopular -> MovieTypeParam.MostPopular
        MovieTypeTable.TopRated -> MovieTypeParam.TopRated
        MovieTypeTable.NowPlaying -> MovieTypeParam.NowPlaying
    }
}