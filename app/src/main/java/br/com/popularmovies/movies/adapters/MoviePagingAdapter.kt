package br.com.popularmovies.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.movies.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MoviePagingAdapter(private val clickListener: MovieClickListener) :
    PagingDataAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false)
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        Glide.with(holder.itemView.context)
            .load(Constants.IMAGE_URL + movie?.poster)
            .transform(CenterCrop(), RoundedCorners(8)) //TODO Check why images cornes problem
            .error(R.drawable.no_photo)
            .transition(DrawableTransitionOptions.withCrossFade(600))
            .into(holder.moviePoster)

        holder.itemView.setOnClickListener {
            clickListener.onMovieClick(movie)
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

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val moviePoster: ImageView = itemView.findViewById(R.id.iv_movie)
}