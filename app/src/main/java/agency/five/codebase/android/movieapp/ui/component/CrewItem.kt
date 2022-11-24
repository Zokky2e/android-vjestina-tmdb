package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CrewItemViewState(
    val id: Int,
    val name: String,
    val profession: String
)

@Composable
fun CrewItem(
    crewItemViewState: CrewItemViewState,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = crewItemViewState.name,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(bottom = 2.dp),
            fontSize = 12.sp,
            color = MaterialTheme.colors.onBackground,
        )
        Text(
            text = crewItemViewState.profession,
            fontSize = 12.sp,
            modifier = Modifier,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
public fun CrewItemPreview() {
    CrewItem(
        crewItemViewState = CrewItemViewState(
            id = 0,
            name = "John Favreau",
            profession = "Director"
        )
    )
}
