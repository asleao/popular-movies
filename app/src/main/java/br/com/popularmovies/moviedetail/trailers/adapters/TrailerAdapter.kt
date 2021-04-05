package br.com.popularmovies.moviedetail.trailers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.entities.movie.MovieTrailer
import br.com.popularmovies.moviedetail.trailers.Constants
import br.com.popularmovies.moviedetail.trailers.adapters.TrailerAdapter.TrailerViewHolder

class TrailerAdapter(private val movieTrailers: List<MovieTrailer>,
                     private val mOnTrailerClickListener: TrailerClickListener) :
        RecyclerView.Adapter<TrailerViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TrailerViewHolder {
        val context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.movie_trailer, viewGroup, false)
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(movieViewHolder: TrailerViewHolder, position: Int) {
        val movieTrailer = movieTrailers[position]
        movieViewHolder.mTitle.text = movieTrailer.name
        movieViewHolder.mMediaPlay.setOnClickListener { mOnTrailerClickListener.onPlay(movieTrailer.key) }
        movieViewHolder.mShare.setOnClickListener { mOnTrailerClickListener.onShare(Constants.YOUTUBE_URL + movieTrailer.key) }
    }

    override fun getItemCount(): Int {
        return movieTrailers.size
    }

    inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mMediaPlay: ImageView = itemView.findViewById(R.id.iv_trailer_play)
        val mTitle: TextView = itemView.findViewById(R.id.tv_trailer_title)
        var mShare: ImageView = itemView.findViewById(R.id.iv_trailer_share)
    }
}