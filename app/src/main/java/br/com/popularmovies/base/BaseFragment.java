package br.com.popularmovies.base;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.popularmovies.R;

import static br.com.popularmovies.movies.Constants.GENERIC_MSG_ERROR_TITLE;

public abstract class BaseFragment extends Fragment {

    protected Group mNoConnectionGroup;
    protected Button mTryAgainButton;
    protected TextView mNoConnectionText;
    protected ProgressBar mProgressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_layout, container, true);
        if (view != null) {
            setupFields(view);
        }
        return view;
    }

    private void setupFields(View view) {
        mNoConnectionGroup = view.findViewById(R.id.group_no_connection);
        mNoConnectionText = view.findViewById(R.id.tv_no_conection);
        mTryAgainButton = view.findViewById(R.id.bt_try_again);
        mProgressBar = view.findViewById(R.id.pb_base);
    }

    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mNoConnectionGroup.setVisibility(View.GONE);
    }

    public void showResult() {
        mNoConnectionGroup.setVisibility(View.GONE);
    }

    public void showNoConnection(String message) {
        hideLoading();
        mNoConnectionText.setText(message);
        mNoConnectionGroup.setVisibility(View.VISIBLE);
    }

    public void showGenericError(String message) {
        final AlertDialog sortDialog = new AlertDialog.Builder(getContext())
                .setTitle(GENERIC_MSG_ERROR_TITLE)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, null)
                .create();

        sortDialog.show();
    }


    public void tryAgain(final Runnable action) {
        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.run();
            }
        });
    }
}
