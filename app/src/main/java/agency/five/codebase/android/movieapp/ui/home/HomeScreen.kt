package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = viewModel(),
    onNavigateToMovieDetails: (Int) -> Unit
) {
    val popularCategoryViewState: HomeMovieCategoryViewState by homeViewModel.popularCategoryViewState.collectAsState()
    val nowPlayingCategoryViewState: HomeMovieCategoryViewState by homeViewModel.nowPlayingCategoryViewState.collectAsState()
    val upcomingCategoryViewState: HomeMovieCategoryViewState by homeViewModel.upcomingCategoryViewState.collectAsState()
    HomeScreen(
        popularCategoryViewState, nowPlayingCategoryViewState, upcomingCategoryViewState,
        onCategoryClick = { category ->
            homeViewModel.changeCategory(category.itemId)
        },
        onNavigateToMovieDetails,
        onFavoriteClick = { movie ->
            homeViewModel.toggleFavorite(movie.id)

        }
    )
}

@Composable
private fun MovieCategorySection(
    categoryViewState: HomeMovieCategoryViewState,
    title: String,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteClick: (MovieCardViewState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(320.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(bottom = LocalSpacing.current.small),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            for (movieCategory in categoryViewState.movieCategories) {
                MovieCategoryLabel(
                    movieCategoryLabelViewState = movieCategory,
                    onItemClick = { onCategoryClick(movieCategory) }
                )
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
        ) {
            items(categoryViewState.movies.count()) {
                MovieCard(
                    movieCardViewState = categoryViewState.movies[it],
                    onCardClick = { onNavigateToMovieDetails(categoryViewState.movies[it].id) },
                    onLikeButtonClick = {
                        onFavoriteClick(
                            categoryViewState.movies[it]
                        )
                    })
            }
        }
    }
}

@Composable
fun HomeScreen(
    popularCategoryViewState: HomeMovieCategoryViewState,
    nowPlayingCategoryViewState: HomeMovieCategoryViewState,
    upcomingCategoryViewState: HomeMovieCategoryViewState,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteClick: (MovieCardViewState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = LocalSpacing.current.medium, start = LocalSpacing.current.medium
            )
    ) {
        item {
            MovieCategorySection(
                popularCategoryViewState, "What's Popular",
                onCategoryClick = onCategoryClick,
                onNavigateToMovieDetails,
                onFavoriteClick,
            )
        }
        item {
            MovieCategorySection(
                nowPlayingCategoryViewState, "Now Playing",
                onCategoryClick = onCategoryClick,
                onNavigateToMovieDetails,
                onFavoriteClick,
            )
        }
        item {
            MovieCategorySection(
                upcomingCategoryViewState, "Upcoming",
                onCategoryClick = onCategoryClick,
                onNavigateToMovieDetails,
                onFavoriteClick,
            )
        }
    }

}
