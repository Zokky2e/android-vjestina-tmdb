package agency.five.codebase.android.movieapp.ui.main

import agency.five.codebase.android.movieapp.navigation.NavigationItem
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.navigation.MOVIE_ID_KEY
import agency.five.codebase.android.movieapp.navigation.MovieDetailsDestination
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewModel
import agency.five.codebase.android.movieapp.ui.home.HomeViewModel
import agency.five.codebase.android.movieapp.ui.home.HomeRoute
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsRoute
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewModel
import agency.five.codebase.android.movieapp.ui.theme.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var currentMovieId = 1
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = when (navBackStackEntry?.destination?.route) {
        MovieDetailsDestination.route -> false
        else -> true
    }
    val showBackIcon = !showBottomBar
    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = {
                    if (showBackIcon) BackIcon(
                        onBackClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(
                            start = LocalSpacing.current.small
                        )
                    )
                }
            )
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination = { navigationItem ->
                        navController.popBackStack(
                            NavigationItem.HomeDestination.route,
                            inclusive = false
                        )
                        navController.navigate(navigationItem.route) { launchSingleTop = true }
                    },
                    currentDestination = navBackStackEntry?.destination
                )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    val homeViewModel: HomeViewModel = get()
                    HomeRoute(
                        homeViewModel,
                        onNavigateToMovieDetails = { id ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(id)
                            )
                        }
                    )
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    val favoritesViewModel: FavoritesViewModel = get()
                    FavoritesRoute(
                        favoritesViewModel,
                        onNavigateToMovieDetails = { id ->
                            navController.navigate(
                                MovieDetailsDestination.createNavigationRoute(id)
                            )
                            currentMovieId = id
                        },
                    )
                }
                composable(
                    route = MovieDetailsDestination.route,
                    arguments = listOf(navArgument(MOVIE_ID_KEY) { type = NavType.IntType }),
                ) {
                    val movieDetailsViewModel: MovieDetailsViewModel = getViewModel(
                        parameters = { parametersOf(currentMovieId) }
                    )
                    MovieDetailsRoute(movieDetailsViewModel)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .background(Blue)
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        navigationIcon?.invoke()
        Image(
            painter = painterResource(id = R.drawable.tmdb_logo),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun BackIcon(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.arrow),
        contentDescription = null,
        modifier = modifier.clickable { onBackClick() },
        alignment = Alignment.CenterStart
    )
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (currentDestination != null) {
                        Image(
                            painter = painterResource(id = if (currentDestination.route == destination.route) destination.selectedIconId else destination.unselectedIconId),
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    onNavigateToDestination(destination)
                                },
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                        )
                    }
                    Text(
                        text = stringResource(id = destination.labelId),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}
