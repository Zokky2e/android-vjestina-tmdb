package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Gray600
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState {
    data class Text(val text: String) : MovieCategoryLabelTextViewState()
    data class TextRes(@StringRes val textRes: Int) : MovieCategoryLabelTextViewState()
}

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)

@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .width(IntrinsicSize.Max)
        .clickable { onItemClick() })
    {
        Text(
            text = when (movieCategoryLabelViewState.categoryText) {
                is MovieCategoryLabelTextViewState.Text -> movieCategoryLabelViewState.categoryText.text
                is MovieCategoryLabelTextViewState.TextRes -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
            },
            fontWeight = if (movieCategoryLabelViewState.isSelected) FontWeight.ExtraBold else FontWeight.Normal,
            color = if (movieCategoryLabelViewState.isSelected) MaterialTheme.colors.secondaryVariant else Gray600,
            fontSize = 16.sp,
            modifier = Modifier

        )
        if (movieCategoryLabelViewState.isSelected) {
            Divider(
                color = MaterialTheme.colors.secondaryVariant,
                thickness = 3.dp,
                modifier = Modifier.padding(top = 3.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCategoryLabelPreview() {
    var isSelected by remember { mutableStateOf(false) }

    MovieCategoryLabel(
        movieCategoryLabelViewState = MovieCategoryLabelViewState(
            itemId = 0,
            isSelected = isSelected,
            categoryText = MovieCategoryLabelTextViewState.Text(text = "Movies")
        ),
        onItemClick = { isSelected = true }
    )
}
