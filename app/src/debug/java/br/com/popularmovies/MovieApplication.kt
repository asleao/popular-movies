package br.com.popularmovies

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import br.com.popularmovies.data.di.DaggerDataComponent
import br.com.popularmovies.data.di.DataComponent
import br.com.popularmovies.datasourcedb.di.DaggerDatabaseComponent
import br.com.popularmovies.datasourceremote.di.DaggerNetworkComponent
import br.com.popularmovies.di.AppComponent
import br.com.popularmovies.di.DaggerAppComponent
import br.com.popularmovies.domain.di.DaggerDomainComponent
import br.com.popularmovies.domain.di.DomainComponent
import coil.ImageLoader
import coil.ImageLoaderFactory

class MovieApplication : Application(), ImageLoaderFactory {
    val databaseComponent by lazy {
        DaggerDatabaseComponent.factory().create(applicationContext)
    }
    val networkComponent = DaggerNetworkComponent.builder().build()
    val dataComponent: DataComponent by lazy {
        DaggerDataComponent
            .builder()
            .databaseComponentProvider(databaseComponent)
            .networkComponentProvider(networkComponent)
            .build()
    }
    val domainComponent: DomainComponent by lazy {
        DaggerDomainComponent.builder().dataComponent(dataComponent).build()
    }
    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .dataComponentProvider(dataComponent)
            .databaseComponentProvider(databaseComponent)
            .networkComponentProvider(networkComponent)
            .domainComponentProvider(domainComponent)
            .build()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}

val Activity.appComponent get() = (application as MovieApplication).appComponent
val Fragment.appComponent get() = (requireActivity().application as MovieApplication).appComponent