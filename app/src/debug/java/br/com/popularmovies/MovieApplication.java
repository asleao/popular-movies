package br.com.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MovieApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
