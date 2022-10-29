package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.R
import agency.five.codebase.android.movieapp.ui.theme.spacing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = if(isFavorite) R.drawable.filled_heart else R.drawable.empty_heart),
                contentDescription = null,
        modifier = modifier
            .size(32.dp)
            .background(Blue, CircleShape)
            .clip(CircleShape)
            .padding(MaterialTheme.spacing.extraSmall)
            .padding(top = 4.dp)
            .clickable{onClick()}
    )
}
@Preview(showBackground = true)
@Composable
public fun FavoriteButtonPreview() {
    var isFavorite by remember { mutableStateOf(true) }
    FavoriteButton(isFavorite = isFavorite,
        onClick = { isFavorite = !isFavorite })

}
