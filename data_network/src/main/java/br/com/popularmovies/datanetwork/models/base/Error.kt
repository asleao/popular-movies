package br.com.popularmovies.datanetwork.models.base

data class Error(val codErro: Int = -1, val title: String = "", val message: String = "") { // TODO Rename this class
    fun isValid(): Boolean = codErro != -1 && title.isNotEmpty() && message.isNotEmpty()
}