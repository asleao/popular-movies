package br.com.popularmovies.core.network.retrofit.model

class Resource<T> private constructor(
        val status: Status,
        val data: T? = null,
        val error: Error? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(
                    Status.SUCCESS,
                    data = data
            )
        }

        fun <T> error(error: Error?): Resource<T> {
            return Resource(
                    Status.ERROR,
                    error = error
            )
        }
    }

    enum class Status {
        SUCCESS, ERROR
    }
}