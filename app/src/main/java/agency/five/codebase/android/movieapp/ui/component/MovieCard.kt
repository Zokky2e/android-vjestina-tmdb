package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


data class MovieCardViewState(
    val id: Int,
    val isFavorite: Boolean,
    val imageUrl: String?,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieCard(
    movieCardViewState: MovieCardViewState,
    onCardClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    modifier: Modifier = Modifier.size(130.dp, 200.dp)
) {
    Card(
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = 10.dp,
        onClick = onCardClick,
        modifier = modifier
    ) {
        Box(modifier = Modifier) {
            AsyncImage(
                model = movieCardViewState.imageUrl,
                contentDescription = movieCardViewState.imageUrl,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop

            )

            FavoriteButton(
                isFavorite = movieCardViewState.isFavorite,
                onClick = onLikeButtonClick,
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
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
            id = 1,
            isFavorite = onLikeButtonClick,
            imageUrl = "https://www.filmizlesene.pro/wp-content/uploads/2010/03/1159.jpg",
        ),
        onCardClick = { onCardClick = !onCardClick },
        onLikeButtonClick = { onLikeButtonClick = !onLikeButtonClick },

        )
}
