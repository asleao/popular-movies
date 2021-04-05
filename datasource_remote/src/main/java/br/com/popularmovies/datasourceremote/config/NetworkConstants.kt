package br.com.popularmovies.datasourceremote.config

internal object NetworkConstants {
    const val SCHEME = "https"
    const val HOST = "api.themoviedb.org"
    const val API_VERSION = "3/"
    const val API_KEY = "api_key"

    const val NETWORK_ERROR_TITLE = "Ops, connection failed. :/"
    const val NETWORK_ERROR_MSG = "Problems with your connection :("
}
