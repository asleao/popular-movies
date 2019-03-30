package br.com.popularmovies.data.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import br.com.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static br.com.popularmovies.data.Constants.API_KEY;

public class AuthorizationInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(API_KEY, BuildConfig.MdbApiKey)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
