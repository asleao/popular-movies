package br.com.popularmovies.movies.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.ui.NowPlayingMovieFragment
import br.com.popularmovies.movies.ui.NowPlayingMovieFragment.Companion.MOVIE_ARG

class NowPlayingViewPagerAdapter(
    fragment: Fragment,
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NowPlayingMovieFragment()
        fragment.arguments = Bundle().apply {
//            putParcelable(MOVIE_ARG, movies[position])
        }
        return fragment
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.itemView.setOnClickListener {
            onMovieClick(movies[position])
        }
        super.onBindViewHolder(holder, position, payloads)
    }
}