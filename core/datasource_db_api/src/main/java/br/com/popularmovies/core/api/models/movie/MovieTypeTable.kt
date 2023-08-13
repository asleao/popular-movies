package br.com.popularmovies.core.api.models.movie

enum class MovieTypeTable(val id: Int) {
    TopRated(0),
    MostPopular(1),
    NowPlaying(2),
    Unknown(3);

    companion object {
        fun forId(id: Int): MovieTypeTable {
            return values().find { it.id == id }
                ?: throw IllegalArgumentException("Invalid MovieTypeTable with id: $id")
        }
    }
}