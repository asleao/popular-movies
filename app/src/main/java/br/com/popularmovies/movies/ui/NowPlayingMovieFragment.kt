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
//            val movieArg = it.getParcelable<Movie>(MOVIE_ARG)
//            movieArg?.let { movie ->
//            binding.ctHeaderImage.load(Constants.IMAGE_URL + movie.poster) {
//                val shimmerDrawable = shimmerDrawable()
//                crossfade(true)
//                placeholder(shimmerDrawable)
//                error(R.drawable.no_photo)
//            }
//            binding.tvHeaderTitle.text = movie.originalTitle
        }
//        }
        return binding.root
    }

    companion object {
        const val MOVIE_ARG = "movie_arg"
    }
}