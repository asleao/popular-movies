package br.com.popularmovies.movies.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fg_movie_navhost) as NavHostFragment?
            ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fg_movie_navhost).navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fg_movie_navhost))
                || super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bnv_navigation)
        bottomNav?.setupWithNavController(navController)
    }
}
