package br.com.popularmovies.movies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.movies.Constants
import br.com.popularmovies.movies.adapters.MovieAdapter.MovieViewHolder
import br.com.popularmovies.services.movieService.response.MovieDto
import com.squareup.picasso.Picasso

class MovieAdapter(private val mOnMovieClickListener: MovieClickListener) : ListAdapter<MovieDto, MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieViewHolder {
        val context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        Picasso.get()
                .load(Constants.IMAGE_URL + movie.poster)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_photo)
                .into(movieViewHolder.moviePoster)
    }

    fun swapData(data: List<MovieDto>) {
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

    private class MovieDiffCallback : DiffUtil.ItemCallback<MovieDto>() {
        override fun areItemsTheSame(
                oldItem: MovieDto,
                newItem: MovieDto
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
                oldItem: MovieDto,
                newItem: MovieDto
        ) = oldItem == newItem
    }

}