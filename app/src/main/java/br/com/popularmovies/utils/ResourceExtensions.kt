package br.com.popularmovies.utils

import androidx.lifecycle.MutableLiveData
import br.com.popularmovies.core.network.retrofit.model.Error
import br.com.popularmovies.core.network.retrofit.model.Resource

fun <T> Resource<T>.validateResponse(success: MutableLiveData<T>, error: MutableLiveData<Error>) {
    when (this.status) {
        Resource.Status.SUCCESS -> {
            this.data?.let {
                success.value = it
            }
        }
        else -> this.error?.let {
            error.value = it
        }
    }
}