package br.com.popularmovies.home.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import br.com.popularmovies.home.ui.NowPlayingMovieFragment
import br.com.popularmovies.model.movie.Movie

class NowPlayingViewPagerAdapter(
    fragment: Fragment,
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = movies.size

    override fun createFragment(position: Int): Fragment {
        val fragment = NowPlayingMovieFragment()
        fragment.arguments = Bundle().apply {
            putString(MOVIE_TITLE_ARG, movies[position].originalTitle)
            putString(MOVIE_POSTER_ARG, movies[position].poster)
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

    companion object {
        const val MOVIE_TITLE_ARG = "movie_title_arg"
        const val MOVIE_POSTER_ARG = "movie_poster_arg"
    }
}