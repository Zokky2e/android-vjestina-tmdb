package agency.five.codebase.android.movieapp.ui.component


import agency.five.codebase.android.movieapp.ui.theme.LightGreen
import agency.five.codebase.android.movieapp.ui.theme.LimeGreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val DEGREES_IN_CIRCLE = 360f
private const val STARTING_POSITION = 90f

@Composable
fun UserScoreProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .size(40.dp)
        .padding(2.dp)){
        Canvas(modifier = Modifier.size(40.dp)) {

            drawArc(
                color = LightGreen,
                startAngle = (progress*DEGREES_IN_CIRCLE)-STARTING_POSITION,
                sweepAngle = (1-progress)*DEGREES_IN_CIRCLE,
            style=Stroke(
                cap=StrokeCap.Round,
                width = 6F
            ),
            useCenter=false)
            drawArc(
                color = LimeGreen,
                startAngle = -STARTING_POSITION,
                sweepAngle = progress*DEGREES_IN_CIRCLE,
                style=Stroke(
                    cap=StrokeCap.Round,
                    width = 6F
                ),
                useCenter=false)

        }
        Text(text =( progress*10).toString(),
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp
            )
        }
}
@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview(){
    UserScoreProgressBar(
        progress = 0.15F
        )
}
