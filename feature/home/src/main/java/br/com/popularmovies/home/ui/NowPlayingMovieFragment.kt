package br.com.popularmovies.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.popularmovies.feature.home.R
import br.com.popularmovies.feature.home.databinding.FragmentNowPlayingMovieBinding
import br.com.popularmovies.common.Constants
import br.com.popularmovies.home.utils.shimmerDrawable
import coil.load
import javax.inject.Inject

class NowPlayingMovieFragment @Inject constructor() : Fragment() {

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