package br.com.popularmovies.moviedetail.reviews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.popularmovies.R
import br.com.popularmovies.services.movieService.response.MovieReview

class ReviewAdapter(private val mReviews: List<MovieReview>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ReviewViewHolder {
        val context = viewGroup.context
        val view = LayoutInflater.from(context).inflate(R.layout.movie_comment, viewGroup, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(movieViewHolder: ReviewViewHolder, position: Int) {
        val movieReview = mReviews[position]
        movieViewHolder.mAuthor.text = "Author: ${movieReview.author}"
        movieViewHolder.mContent.text = movieReview.content

    }

    override fun getItemCount(): Int {
        return mReviews.size
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mAuthor: TextView = itemView.findViewById(R.id.tv_comment_author)
        val mContent: TextView = itemView.findViewById(R.id.tv_comment_message)

    }
}
