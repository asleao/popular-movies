package br.com.popularmovies.common.models.base

data class NetworkError(val code: Int = -1, val title: String = "", val message: String = "") {
    fun isValid(): Boolean = code != -1 && title.isNotEmpty() && message.isNotEmpty()
}