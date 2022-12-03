package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FavoritesRoute(
    favoritesViewModel: FavoritesViewModel = viewModel(),
    onNavigateToMovieDetails: (Int) -> Unit
) {
    val favoritesViewState: FavoritesViewState by favoritesViewModel.favoritesViewState.collectAsState()
    FavoritesScreen(
        favoritesViewState,
        onNavigateToMovieDetails,
        { movie ->
            favoritesViewModel.toggleFavorite(movie.id)
        },
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
