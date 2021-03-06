package br.com.popularmovies.datanetwork.interceptors

import br.com.popularmovies.datanetwork.BuildConfig
import br.com.popularmovies.datanetwork.config.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthorizationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.MdbApiKey)
                .build()

        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
