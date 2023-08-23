package br.com.popularmovies.datasourceremote.models.exceptions

class NoConnectionException(
    message: String?,
    cause: Throwable?
) : Throwable(message, cause)