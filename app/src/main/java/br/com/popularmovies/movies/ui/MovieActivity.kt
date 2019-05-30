package br.com.popularmovies.movies.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.popularmovies.R
import br.com.popularmovies.services.movieService.source.MovieRepository
import br.com.popularmovies.services.movieService.source.local.MovieLocalDataSource
import br.com.popularmovies.services.movieService.source.remote.MovieRemoteDataSource


class MovieActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val host: NavHostFragment = supportFragmentManager
                .findFragmentById(R.id.fg_movie_navhost) as NavHostFragment?
                ?: return

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBar(navController, appBarConfiguration)
    }

    private fun setupActionBar(
            navController: NavController,
            appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fg_movie_navhost).navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fg_movie_navhost))
                || super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        MovieRepository.destroyInstance()
        MovieLocalDataSource.destroyInstance()
        MovieRemoteDataSource.destroyInstance()
    }
}
