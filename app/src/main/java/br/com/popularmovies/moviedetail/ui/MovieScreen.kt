package br.com.popularmovies.moviedetail.ui

import androidx.compose.runtime.Composable
import br.com.popularmovies.entities.movie.Movie
import br.com.popularmovies.moviedetail.viewmodel.MovieDetailViewModel

@Composable
fun MovieScreen(movie: Movie, viewModel: MovieDetailViewModel) {
    MovieDetail(movie)
}