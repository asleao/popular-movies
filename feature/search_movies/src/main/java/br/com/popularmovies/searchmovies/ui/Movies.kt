package br.com.popularmovies.searchmovies.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.popularmovies.core.designsystem.AppTheme
import br.com.popularmovies.core.designsystem.previews.ThemePreview
import br.com.popularmovies.model.movie.Movie
import br.com.popularmovies.model.movie.MovieType
import org.joda.time.LocalDate
import java.math.BigDecimal

@Composable
fun Movies(
    modifier: Modifier,
    movies: List<Movie>,
    onMovieSelected: (Movie) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie, onMovieSelected)
        }
    }
}

@ThemePreview
@Composable
fun MoviesPreview() {
    AppTheme {
        val movie = Movie(
            votes = 1,
            id = 1L,
            type = MovieType.MostPopular,
            voteAverage = BigDecimal.TEN,
            originalTitle = "Movie Title",
            popularity = BigDecimal.TEN,
            poster = "",
            overview = "",
            releaseDate = LocalDate.now()
        )
        Movies(
            modifier = Modifier,
            movies = listOf(movie, movie)
        ) {}
    }
}