package agency.five.codebase.android.movieapp.ui.component

import agency.five.codebase.android.movieapp.ui.theme.Blue
import agency.five.codebase.android.movieapp.ui.theme.Gray600
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class MovieCategoryLabelTextViewState

class MovieCategoryLabelTextViewStateDirect(val text: String): MovieCategoryLabelTextViewState()

class MovieCategoryLabelTextViewStateReferenced(@StringRes val textRes: Int): MovieCategoryLabelTextViewState()

data class MovieCategoryLabelViewState(
    val itemId: Int,
    val isSelected: Boolean,
    val categoryText: MovieCategoryLabelTextViewState
)
@Composable
fun MovieCategoryLabel(
    movieCategoryLabelViewState: MovieCategoryLabelViewState,
    modifier: Modifier = Modifier
) {
    var isSelectedState by remember { mutableStateOf(movieCategoryLabelViewState.isSelected) }
    Column(modifier = modifier.width(IntrinsicSize.Min))
    {
     Text(
         text = when (movieCategoryLabelViewState.categoryText)

         {
             is MovieCategoryLabelTextViewStateDirect -> movieCategoryLabelViewState.categoryText.text
             is MovieCategoryLabelTextViewStateReferenced -> stringResource(id = movieCategoryLabelViewState.categoryText.textRes)
         },
         fontWeight = if(isSelectedState) FontWeight.ExtraBold else FontWeight.Normal,
         color = Gray600,
         fontSize = 12.sp,
         modifier = modifier.clickable{isSelectedState = isSelectedState.not()}

     )
        if(isSelectedState){
            Divider(
                color = Blue,
                thickness = 3.dp,
                modifier = modifier.padding(top = 3.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCategoryLabelPreview(){
    MovieCategoryLabel(
        movieCategoryLabelViewState = MovieCategoryLabelViewState(
            itemId = 0,
            isSelected = false,
            categoryText = MovieCategoryLabelTextViewStateDirect(text="Movies")
        )
    )
}