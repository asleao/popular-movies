package br.com.popularmovies.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.popularmovies.R
import br.com.popularmovies.databinding.FragmentNowPlayingMovieBinding
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.utils.shimmerDrawable
import br.com.popularmovies.utils.shimmerRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class NowPlayingMovieFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNowPlayingMovieBinding.inflate(inflater, container, false)
        arguments?.let {
            val movieArg = it.getParcelable<Movie>(MOVIE_ARG)
            movieArg?.let { movie ->
                binding.container.startShimmer()

                Glide.with(requireContext())
                    .load(Constants.IMAGE_URL + movie.poster)
                    .placeholder(shimmerDrawable())
                    .transform(CenterCrop())
                    .error(R.drawable.no_photo)
                    .listener(shimmerRequestListener(binding.container))
                    .transition(DrawableTransitionOptions.withCrossFade(600))
                    .into(binding.ctHeaderImage)
                binding.tvHeaderTitle.text = movie.originalTitle
            }
        }
        return binding.root
    }

    companion object {
        const val MOVIE_ARG = "movie_arg"
    }
}