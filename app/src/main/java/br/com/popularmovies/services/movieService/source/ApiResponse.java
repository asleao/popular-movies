package br.com.popularmovies.services.movieService.source;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import br.com.popularmovies.data.model.ErrorResponse;
import br.com.popularmovies.data.model.OldResource;
import retrofit2.Response;

import static br.com.popularmovies.core.network.NetworkConstantsKt.GENERIC_ERROR_CODE;
import static br.com.popularmovies.core.network.NetworkConstantsKt.NETWORK_ERROR_CODE;
import static br.com.popularmovies.movies.Constants.CONNECTION_MSG_ERROR;
import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_MESSAGE;
import static br.com.popularmovies.movies.Constants.SERVER_MSG_ERROR;

public class ApiResponse<T> {
    private final String logTag;

    public ApiResponse(String logTag) {
        this.logTag = logTag;
    }

    public OldResource<T> getApiOnResponse(Response<T> response) {
        if (response.body() != null) {
            if (response.isSuccessful()) {
                return OldResource.success(response.body());
            } else {
                if (response.errorBody() != null) {
                    ErrorResponse error = null;
                    if (response.code() >= 400 && response.code() < GENERIC_ERROR_CODE) {
                        Moshi moshi = new Moshi.Builder().build();
                        JsonAdapter<ErrorResponse> jsonAdapter = moshi.adapter(ErrorResponse.class);
                        try {
                            error = jsonAdapter.fromJson(response.errorBody().string());
                        } catch (IOException e) {
                            Log.e(logTag, e.getMessage());
                        }
                    } else {
                        error = new ErrorResponse(response.code(),
                                SERVER_MSG_ERROR);
                    }
                    return OldResource.error(error);
                }
            }
        }

        return OldResource.error(new ErrorResponse(response.code(),
                SERVER_MSG_ERROR));
    }

    public OldResource<T> getApiOnFailure(Throwable t) {
        ErrorResponse error;
        if (t instanceof IOException) {
            error = new ErrorResponse(NETWORK_ERROR_CODE,
                    CONNECTION_MSG_ERROR);
        } else {
            error = new ErrorResponse(GENERIC_ERROR_CODE,
                    GENERIC_MSG_ERROR_MESSAGE);
        }
        Log.e(logTag, t.getMessage());
        return OldResource.error(error);
    }
}
