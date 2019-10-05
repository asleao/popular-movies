package br.com.popularmovies.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OldResource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final ErrorResponse error;

    private OldResource(@NonNull Status status, @Nullable T data,
                        @Nullable ErrorResponse error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> OldResource<T> success(T data) {
        return new OldResource<>(Status.SUCCESS, data, null);
    }

    public static <T> OldResource<T> error(ErrorResponse error) {
        return new OldResource<>(Status.ERROR, null, error);
    }

    public static <T> OldResource<T> loading() {
        return new OldResource<>(Status.LOADING, null, null);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
