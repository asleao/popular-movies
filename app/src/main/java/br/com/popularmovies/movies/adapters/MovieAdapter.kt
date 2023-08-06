package br.com.popularmovies.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.movies.adapters.MovieAdapter.MovieViewHolder

class MovieAdapter(private val mOnMovieClickListener: MovieClickListener) : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
//        Glide.with(holder.itemView.context)
//            .load(Constants.IMAGE_URL + movie?.poster)
//            .transform(CenterCrop(), RoundedCorners(8))
//            .error(R.drawable.no_photo)
//            .transition(DrawableTransitionOptions.withCrossFade(600))
//            .into(holder.moviePoster)
    }

    fun swapData(data: List<Movie>) {
        submitList(data.toMutableList())
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val moviePoster: ImageView = itemView.findViewById(R.id.iv_movie)

        override fun onClick(v: View) {
            val clicked = getItem(adapterPosition)
            mOnMovieClickListener.onMovieClick(clicked)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
        ) = oldItem == newItem
    }

}