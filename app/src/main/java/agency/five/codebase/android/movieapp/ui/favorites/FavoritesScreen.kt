package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.movieapp.ui.favorites.mapper.FavoritesMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


private val favoritesMapper: FavoritesMapper = FavoritesMapperImpl()

// multiple view states if required
var favoritesViewState = favoritesMapper.toFavoritesViewState(MoviesMock.getMoviesList())

@Composable
fun FavoritesRoute(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    var f by remember { mutableStateOf(favoritesViewState) }
// ...
    FavoritesScreen(
        f,
        onNavigateToMovieDetails,
        { favoritesMovieViewState ->
            f = changeMovieFavoriteStatus(f, favoritesMovieViewState)
        },
// other states and actions
    )
}

@Composable
private fun MoviesList(
    movieItems: List<MovieCardViewState>,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (MovieCardViewState) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = LocalSpacing.current.medium),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.extraSmall)
    ) {
        items(movieItems.count()) {
            MovieCard(
                movieCardViewState = movieItems[it],
                onLikeButtonClick = { onFavoriteButtonClick(movieItems[it]) },
                onCardClick = { onNavigateToMovieDetails(movieItems[it].id) }
            )

        }
    }
}

@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteButtonClick: (MovieCardViewState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.small, LocalSpacing.current.medium)
    ) {
        Text(text = "Favorites", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        MoviesList(
            movieItems = favoritesViewState.movieCardViewStates,
            onNavigateToMovieDetails,
            onFavoriteButtonClick
        )
    }

}

@Preview
@Composable
fun FavoritesScreenPreview() {
    var favoritesViewState = favoritesMapper.toFavoritesViewState(MoviesMock.getMoviesList())
    MovieAppTheme {
        FavoritesScreen(
            favoritesViewState,
            { },
            { favoritesMovieViewState ->
                favoritesViewState =
                    changeMovieFavoriteStatus(favoritesViewState, favoritesMovieViewState)
            },
        )
    }
}

fun changeMovieFavoriteStatus(
    favoritesViewState: FavoritesViewState,
    movie: MovieCardViewState
): FavoritesViewState {
    val movies = favoritesViewState.movieCardViewStates.map {
        if (movie == it) movie.copy(isFavorite = !it.isFavorite) else it
    }
    return favoritesViewState.copy(movieCardViewStates = movies)
}
