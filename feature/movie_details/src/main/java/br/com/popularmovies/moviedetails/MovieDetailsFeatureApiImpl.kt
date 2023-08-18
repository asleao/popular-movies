package br.com.popularmovies.moviedetails

import android.net.Uri
import androidx.core.net.toUri
import br.com.popularmovies.feature.moviedetails.api.MovieDetailsFeatureApi
import javax.inject.Inject

class MovieDetailsFeatureApiImpl @Inject constructor() : MovieDetailsFeatureApi {

    override fun deeplink(argument: String): Uri =
        "popularmovies://movieDetailFragment/${argument}".toUri()

}