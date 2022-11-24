package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {

    override fun toFavoritesViewState(favoriteMovies: List<Movie>) =
        FavoritesViewState(
            movieCardViewStates = favoriteMovies.map {
                MovieCardViewState(
                    it.id,
                    it.isFavorite,
                    it.imageUrl
                )
            }.filter { it.isFavorite }
        )
}
