package br.com.popularmovies.moviedetails

import android.net.Uri
import androidx.core.net.toUri
import br.com.popularmovies.model.feature.FeatureApi
import javax.inject.Inject

class MovieDetailsFeatureApiImpl @Inject constructor() : FeatureApi {

    override fun deeplink(argument: String): Uri =
        "popularmovies://movieDetailFragment/${argument}".toUri()

}