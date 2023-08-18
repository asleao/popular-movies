package br.com.popularmovies.model.feature

import android.net.Uri

interface FeatureApi {
    fun deeplink(argument: String): Uri
}