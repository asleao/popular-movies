package br.com.popularmovies.datasourceremote.models.exceptions

class NetworkException(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)