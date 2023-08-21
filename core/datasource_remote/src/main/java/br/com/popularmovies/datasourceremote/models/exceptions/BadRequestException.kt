package br.com.popularmovies.datasourceremote.models.exceptions

class BadRequestException(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message, cause)