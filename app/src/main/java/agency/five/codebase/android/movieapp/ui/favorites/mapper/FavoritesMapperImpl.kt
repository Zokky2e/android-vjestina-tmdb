package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {

    override fun toFavoritesViewState(favoriteMovies: List<Movie>): FavoritesViewState {
        val favoritesViewState = FavoritesViewState()
        for (favoriteMovie in favoriteMovies) {
            val movieCardViewState = MovieCardViewState(
                favoriteMovie.id,
                favoriteMovie.isFavorite,
                favoriteMovie.imageUrl,
            )
            if(movieCardViewState.isFavorite){
                favoritesViewState.movieCardViewStates += movieCardViewState
            }
        }
        return favoritesViewState
    }
}
