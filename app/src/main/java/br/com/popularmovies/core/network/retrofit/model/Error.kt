package br.com.popularmovies.core.network.retrofit.model

data class Error(val codErro: Int = -1, val title: String = "", val message: String = "") {
    fun isValid(): Boolean = codErro != -1 && title.isNotEmpty() && message.isNotEmpty()
}