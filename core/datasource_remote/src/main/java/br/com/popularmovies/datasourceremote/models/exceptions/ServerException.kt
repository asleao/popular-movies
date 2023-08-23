package br.com.popularmovies.datasourceremote.models.exceptions

class ServerException(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)