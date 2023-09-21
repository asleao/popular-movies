package br.com.popularmovies

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.work.Configuration
import androidx.work.WorkerFactory
import br.com.popularmovies.common.di.CommonComponent
import br.com.popularmovies.common.di.DaggerCommonComponent
import br.com.popularmovies.data.di.DaggerDataComponent
import br.com.popularmovies.data.di.DataComponent
import br.com.popularmovies.datasourcedb.di.DaggerDatabaseComponent
import br.com.popularmovies.datasourcedb.di.DatabaseComponent
import br.com.popularmovies.datasourceremote.di.DaggerNetworkComponent
import br.com.popularmovies.datasourceremote.di.NetworkComponent
import br.com.popularmovies.di.AppComponent
import br.com.popularmovies.di.AppProvider
import br.com.popularmovies.di.DaggerAppComponent
import br.com.popularmovies.domain.di.DaggerDomainComponent
import br.com.popularmovies.domain.di.DomainComponent
import br.com.popularmovies.home.di.DaggerHomeComponent
import br.com.popularmovies.home.di.HomeComponent
import br.com.popularmovies.moviedetails.di.DaggerMovieDetailsComponent
import br.com.popularmovies.moviedetails.di.MovieDetailsComponent
import br.com.popularmovies.worker.di.DaggerWorkerComponent
import br.com.popularmovies.worker.di.WorkerComponent
import coil.ImageLoader
import coil.ImageLoaderFactory
import javax.inject.Inject

class MovieApplication @Inject constructor() : Application(), ImageLoaderFactory,
    Configuration.Provider {

    val commonComponent: CommonComponent by lazy {
        DaggerCommonComponent.factory().create(
            applicationContext
        )
    }
    val databaseComponent: DatabaseComponent by lazy {
        DaggerDatabaseComponent.factory().create(applicationContext)
    }
    val networkComponent: NetworkComponent = DaggerNetworkComponent.builder().build()
    val dataComponent: DataComponent by lazy {
        DaggerDataComponent.factory()
            .create(
                databaseComponent,
                networkComponent
            )
    }
    val domainComponent: DomainComponent by lazy {
        DaggerDomainComponent.builder().dataComponentProvider(dataComponent).build()
    }

    val homeComponent: HomeComponent by lazy {
        DaggerHomeComponent
            .builder()
            .domainComponentProvider(domainComponent)
            .commonProvider(commonComponent)
            .build()
    }

    val movieDetailsComponent: MovieDetailsComponent by lazy {
        DaggerMovieDetailsComponent
            .builder()
            .domainComponentProvider(domainComponent)
            .commonProvider(commonComponent)
            .build()
    }

    val workerComponent: WorkerComponent by lazy {
        DaggerWorkerComponent
            .factory()
            .create(applicationContext, dataComponent)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .commonProvider(commonComponent)
            .dataComponentProvider(dataComponent)
            .databaseComponentProvider(databaseComponent)
            .networkComponentProvider(networkComponent)
            .domainComponentProvider(domainComponent)
            .homeFeatureProvider(homeComponent)
            .movieDetailsFeatureProvider(movieDetailsComponent)
            .workerComponentProvider(workerComponent)
            .build()
    }

    val appProvider: AppProvider by lazy {
        appComponent
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }

    @Inject
    lateinit var workerFactory: WorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
    }
}

val Activity.appComponent get() = (application as MovieApplication).appComponent
val Fragment.appComponent get() = (requireActivity().application as MovieApplication).appComponent

val Application.appProvider: AppProvider
    get() = (this as MovieApplication).appProvider