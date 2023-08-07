package br.com.popularmovies

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import br.com.popularmovies.di.AppComponent
import br.com.popularmovies.di.DaggerAppComponent
import coil.ImageLoader
import coil.ImageLoaderFactory

class MovieApplication : Application(), ImageLoaderFactory {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}

val Activity.appComponent get() = (application as MovieApplication).appComponent
val Fragment.appComponent get() = (requireActivity().application as MovieApplication).appComponent