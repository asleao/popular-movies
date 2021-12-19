package br.com.popularmovies.movies.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import br.com.popularmovies.MovieApplication
import br.com.popularmovies.R


class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fg_movie_navhost).navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.fg_movie_navhost))
                || super.onOptionsItemSelected(item)
    }
}
