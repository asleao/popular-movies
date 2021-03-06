package br.com.popularmovies.utils

import androidx.lifecycle.MutableLiveData
import br.com.popularmovies.datanetwork.models.base.Error
import br.com.popularmovies.datanetwork.models.base.Result

fun <T> Result<T>.validateResponse(success: MutableLiveData<T>, error: MutableLiveData<Error>) {
    when (this) {
        is Result.Success -> {
            this.data?.let {
                success.value = it
            }
        }
        is Result.Error ->
            error.value = this.error
    }
}