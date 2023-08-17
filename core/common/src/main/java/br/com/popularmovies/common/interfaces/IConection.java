package br.com.popularmovies.common.interfaces;

public interface IConection {
    void showLoading();

    void hideLoading();

    void showResult();

    void showNoConnection(String message);

    void showGenericError(String message);

    void tryAgain();
}
