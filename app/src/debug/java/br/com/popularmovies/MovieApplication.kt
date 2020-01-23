package br.com.popularmovies

import android.app.Activity
import android.app.Application
import br.com.popularmovies.di.AppComponent
import br.com.popularmovies.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class MovieApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    val Activity.component get() = (application as MovieApplication).appComponent

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}