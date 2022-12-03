package agency.five.codebase.android.movieapp.ui.moviedetails

import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.component.*
import agency.five.codebase.android.movieapp.ui.theme.LocalSpacing
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlin.math.ceil
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MovieDetailsRoute(
    movieDetailsViewModel: MovieDetailsViewModel = viewModel()
) {
    val movieDetailsViewState:
            MovieDetailsViewState by
    movieDetailsViewModel.movieDetailsViewState.collectAsState()
    MovieDetailsScreen(
        movieDetailsViewState,
        {
            movieDetailsViewModel.toggleFavorite(movieDetailsViewState.id)
        },
    )
}

@Composable
private fun MovieTitleCard(
    title: String,
    imageUrl: String?,
    voteAverage: Float,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .padding(LocalSpacing.current.medium)
                .height(120.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .width(100.dp)
            ) {
                UserScoreProgressBar(progress = voteAverage)
                Text(
                    text = stringResource(id = R.string.user_score),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                color = MaterialTheme.colors.onPrimary,
            )
            FavoriteButton(isFavorite = isFavorite, onClick = { onFavoriteClick() })
        }
    }
}

@Composable
private fun MovieOverviewSection(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Overview",
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier,
            fontSize = 20.sp
        )
        Text(text = text)
    }
}

@Composable
private fun MovieCrewSection(
    crew: List<CrewItemViewState>,
    modifier: Modifier = Modifier
) {
    val iterations = (ceil(crew.count() / 3.0)).toInt()
    var index = 0
    Column(
        modifier = modifier
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        var it = 0
        while (it < iterations) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(60.dp)
            ) {
                for (x in index..index + 2) {
                    CrewItem(crewItemViewState = crew[index])
                    index++
                }
            }
            it++
        }
    }
}

@Composable
private fun MovieCastSection(
    cast: List<ActorCardViewState>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(360.dp)
            .padding(top = 20.dp, start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)

    ) {
        Text(
            text = "Top Billed Cast",
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier,
            fontSize = 20.sp
        )
        LazyRow(
            modifier = Modifier
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(cast.count()) {
                ActorCard(
                    actorCardViewState = cast[it],
                    height = 200
                )
            }
        }
    }
}

@Composable
fun MovieDetailsScreen(
    movie: MovieDetailsViewState,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            MovieTitleCard(
                title = movie.title,
                imageUrl = movie.imageUrl,
                voteAverage = movie.voteAverage,
                isFavorite = movie.isFavorite,
                onFavoriteClick
            )
        }
        item {
            MovieOverviewSection(
                text = movie.overview
            )
        }
        item {
            MovieCrewSection(
                crew = movie.crew
            )
        }
        item {
            MovieCastSection(
                cast = movie.cast
            )
        }
    }
}
