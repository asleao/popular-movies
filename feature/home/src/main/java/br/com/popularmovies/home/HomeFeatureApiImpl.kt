package br.com.popularmovies.home

import android.net.Uri
import androidx.core.net.toUri
import br.com.popularmovies.home.api.HomeFeatureApi
import javax.inject.Inject

class HomeFeatureApiImpl @Inject constructor() : HomeFeatureApi {
    override fun deeplink(argument: String): Uri =
        "popularmovies://movieFragment".toUri()
}