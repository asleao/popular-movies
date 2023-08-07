package br.com.popularmovies.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.popularmovies.R
import br.com.popularmovies.databinding.FragmentNowPlayingMovieBinding
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.utils.shimmerDrawable
import coil.load

class NowPlayingMovieFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNowPlayingMovieBinding.inflate(inflater, container, false)
        arguments?.let {
            val movieTitleArg = it.getString(MOVIE_TITLE_ARG)
            val moviePosterArg = it.getString(MOVIE_POSTER_ARG)
            binding.ctHeaderImage.load(Constants.IMAGE_URL + moviePosterArg) {
                val shimmerDrawable = shimmerDrawable()
                crossfade(true)
                placeholder(shimmerDrawable)
                error(R.drawable.no_photo)
            }
            binding.tvHeaderTitle.text = movieTitleArg
        }
        return binding.root
    }

    companion object {
        const val MOVIE_TITLE_ARG = "movie_title_arg"
        const val MOVIE_POSTER_ARG = "movie_poster_arg"
    }
}