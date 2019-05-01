package br.com.popularmovies.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class FragmentUtils {

    public static void addFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int fragmentId,
                                             @NonNull String tag,
                                             boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(fragmentId, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment,
                                                 int fragmentId,
                                                 @NonNull String tag,
                                                 boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentId, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }
}
