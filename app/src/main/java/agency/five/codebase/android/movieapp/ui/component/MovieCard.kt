package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


data class MovieCardViewState(
    val imageUrl: String,
    val isFavorite: Boolean
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onCardClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(percent = 10),
        elevation = 10.dp,
        onClick = onCardClick,
        modifier = modifier
    ) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = movieCardViewState.imageUrl,
                contentDescription = movieCardViewState.imageUrl,
                modifier
                    .size(130.dp, 200.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.Crop

            )

            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                onClick = onLikeButtonClick,
                modifier = modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .clickable{onLikeButtonClick()}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
public fun MovieCardPreview() {
    var onCardClick by remember { mutableStateOf(true) }
    var onLikeButtonClick by remember { mutableStateOf(true) }
    MovieCard(
        movieCardViewState = MovieCardViewState(
            imageUrl = "https://www.filmizlesene.pro/wp-content/uploads/2010/03/1159.jpg",
            isFavorite = onLikeButtonClick
        ),
        onCardClick = { onCardClick = !onCardClick },
        onLikeButtonClick = { onLikeButtonClick = !onLikeButtonClick },

        )
}
