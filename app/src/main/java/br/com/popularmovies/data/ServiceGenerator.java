package br.com.popularmovies.data;

import com.squareup.moshi.Moshi;

import br.com.popularmovies.BuildConfig;
import br.com.popularmovies.data.interceptor.AuthorizationInterceptor;
import br.com.popularmovies.utils.BigDecimalAdapter;
import br.com.popularmovies.utils.DateAdapter;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ServiceGenerator {
    private static final String SCHEME = "https";
    private static final String HOST = "api.themoviedb.org";
    private static final String API_VERSION = "3/";
    private static final String API_DATE_PATTERN = "yyyy-mm-dd";

    private static final Moshi moshiFactory = new Moshi.Builder()
            .add(new BigDecimalAdapter())
            .add(new DateAdapter(API_DATE_PATTERN))
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(buildUrl())
                    .addConverterFactory(MoshiConverterFactory.create(moshiFactory));

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addInterceptor(new AuthorizationInterceptor());

    public static <S> S createService(
            Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging) && BuildConfig.DEBUG) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    private static HttpUrl buildUrl() {
        return new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(API_VERSION)
                .build();
    }
}
