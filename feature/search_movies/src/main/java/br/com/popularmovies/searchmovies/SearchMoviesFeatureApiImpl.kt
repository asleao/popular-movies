package br.com.popularmovies.searchmovies

import android.net.Uri
import androidx.core.net.toUri
import br.com.popularmovies.searchmovies.api.SearchMoviesFeatureApi
import javax.inject.Inject

class SearchMoviesFeatureApiImpl @Inject constructor() : SearchMoviesFeatureApi {

    override fun deeplink(argument: String?): Uri =
        "popularmovies://searchMoviesFragment".toUri()

}