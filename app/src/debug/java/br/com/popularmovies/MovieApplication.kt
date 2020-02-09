package br.com.popularmovies

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import br.com.popularmovies.di.AppComponent
import br.com.popularmovies.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class MovieApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}

val Activity.appComponent get() = (application as MovieApplication).appComponent
val Fragment.appComponent get() = (requireActivity().application as MovieApplication).appComponent