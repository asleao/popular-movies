package br.com.popularmovies

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class MovieActivity @Inject constructor() : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fg_movie_navhost) as NavHostFragment?
            ?: return

        val navController = host.navController

        setupBottomNavMenu(navController)
    }

//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        navController?.handleDeepLink(intent)
//    }

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
