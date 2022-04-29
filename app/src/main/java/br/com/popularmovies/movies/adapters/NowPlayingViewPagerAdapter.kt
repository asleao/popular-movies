package br.com.popularmovies.movies.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.ui.NowPlayingMovieFragment
import br.com.popularmovies.movies.ui.NowPlayingMovieFragment.Companion.MOVIE_ARG

class NowPlayingViewPagerAdapter(
    fragment: Fragment,
    private val movies: List<Movie>
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NowPlayingMovieFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(MOVIE_ARG, movies[position])
        }
        return fragment
    }
}