package agency.five.codebase.android.movieapp.ui.home

import agency.five.codebase.android.movieapp.mock.MoviesMock
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCard
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabel
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapper
import agency.five.codebase.android.movieapp.ui.home.mapper.HomeScreenMapperImpl
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import agency.five.codebase.android.movieapp.ui.theme.MovieAppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Collections.copy
import kotlin.math.log

private val homeScreenMapper: HomeScreenMapper = HomeScreenMapperImpl()

// multiple view states if required
val popularCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.POPULAR_STREAMING,
        MovieCategory.POPULAR_ONTV,
        MovieCategory.POPULAR_RENT,
        MovieCategory.POPULAR_THEATERS
    ),
    MovieCategory.POPULAR_STREAMING,
    MoviesMock.getMoviesList()
)
val nowPlayingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.PLAYING_MOVIES,
        MovieCategory.PLAYING_TV,
    ),
    MovieCategory.PLAYING_MOVIES,
    MoviesMock.getMoviesList()
)
val upcomingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
    listOf(
        MovieCategory.UPCOMING_TODAY,
        MovieCategory.UPCOMING_WEEK,
    ),
    MovieCategory.UPCOMING_TODAY,
    MoviesMock.getMoviesList()
)

@Composable
fun HomeRoute(
    onNavigateToMovieDetails: (Int) -> Unit
) {
    var p by remember { mutableStateOf(popularCategoryViewState) }
    var n by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var u by remember { mutableStateOf(upcomingCategoryViewState) }
// ...
    HomeScreen(
        p, n, u,
        onCategoryClick = { category ->
            when (category.itemId) {
                in 0..3 -> {
                    p =
                        changeCategory(p, category.itemId)
                }
                in 4..5 -> {
                    n =
                        changeCategory(u, category.itemId)
                }
                else -> {
                    u =
                        changeCategory(u, category.itemId)
                }
            }
        },
        onNavigateToMovieDetails,
        onFavoriteClick = { category, movie ->
            when (category.itemId) {

                in 0..3 -> {
                    p =
                        changeMovieFavoriteStatus(p, movie)
                }
                in 4..5 -> {
                    n =
                        changeMovieFavoriteStatus(n, movie)
                }
                else -> {
                    u =
                        changeMovieFavoriteStatus(u, movie)
                }
            }
        }
    )
}

@Composable
fun MovieCategorySection(
    categoryViewState: HomeMovieCategoryViewState,
    title: String,
    onCategoryClick: (MovieCategoryLabelViewState) -> Unit,
    onNavigateToMovieDetails: (Int) -> Unit,
    onFavoriteClick: (MovieCategoryLabelViewState, MovieCardViewState) -> Unit,
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
                            categoryViewState.movieCategories[it],
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
    onFavoriteClick: (MovieCategoryLabelViewState, MovieCardViewState) -> Unit,
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

@Preview
@Composable
fun HomeScreenPreview() {
    var popularCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        listOf(
            MovieCategory.POPULAR_STREAMING,
            MovieCategory.POPULAR_ONTV,
            MovieCategory.POPULAR_RENT,
            MovieCategory.POPULAR_THEATERS
        ),
        MovieCategory.POPULAR_STREAMING,
        MoviesMock.getMoviesList()
    )
    var nowPlayingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        listOf(
            MovieCategory.PLAYING_MOVIES,
            MovieCategory.PLAYING_TV,
        ),
        MovieCategory.PLAYING_MOVIES,
        MoviesMock.getMoviesList()
    )
    var upcomingCategoryViewState = homeScreenMapper.toHomeMovieCategoryViewState(
        listOf(
            MovieCategory.UPCOMING_TODAY,
            MovieCategory.UPCOMING_WEEK,
        ),
        MovieCategory.UPCOMING_TODAY,
        MoviesMock.getMoviesList()
    )
    var popularCategoryVS by remember { mutableStateOf(popularCategoryViewState) }
    var nowPlayingCategoryVS by remember { mutableStateOf(nowPlayingCategoryViewState) }
    var upcomingCategoryVS by remember { mutableStateOf(upcomingCategoryViewState) }

    MovieAppTheme {
        HomeScreen(
            popularCategoryVS,
            nowPlayingCategoryVS,
            upcomingCategoryVS,
            onCategoryClick = { category ->
                when (category.itemId) {
                    in 0..3 -> {
                        popularCategoryVS =
                            changeCategory(popularCategoryVS, category.itemId)
                    }
                    in 4..5 -> {
                        nowPlayingCategoryVS =
                            changeCategory(nowPlayingCategoryVS, category.itemId)
                    }
                    else -> {
                        upcomingCategoryVS =
                            changeCategory(upcomingCategoryVS, category.itemId)
                    }

                }
            },
            { },
            onFavoriteClick = { category, movie ->
                when (category.itemId) {

                    in 0..3 -> {
                        popularCategoryViewState =
                            changeMovieFavoriteStatus(popularCategoryViewState, movie)
                    }
                    in 4..5 -> {
                        nowPlayingCategoryViewState =
                            changeMovieFavoriteStatus(nowPlayingCategoryViewState, movie)
                    }
                    else -> {
                        upcomingCategoryViewState =
                            changeMovieFavoriteStatus(upcomingCategoryViewState, movie)
                    }
                }
            }

        )
    }
}

fun changeMovieFavoriteStatus(
    homeMovieCategoryViewState: HomeMovieCategoryViewState,
    movie: MovieCardViewState
): HomeMovieCategoryViewState {
    val movies = homeMovieCategoryViewState.movies.map {
        if (movie == it) movie.copy(isFavorite = !it.isFavorite) else it
    }
    return homeMovieCategoryViewState.copy(movies = movies)
}

fun changeCategory(
    state: HomeMovieCategoryViewState,
    itemId: Int
): HomeMovieCategoryViewState {
    val movieCategories = state.movieCategories.map {
        it.copy(isSelected = itemId == it.itemId)
    }
    return state.copy(movieCategories = movieCategories)
}
