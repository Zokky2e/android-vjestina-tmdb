package agency.five.codebase.android.movieapp.ui.component


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                Color(red=212, green =255,blue=212),
                startAngle = (progress*360)-90,
                sweepAngle = (1-progress)*360,
            style=Stroke(
                cap=StrokeCap.Round,
                width = 6F
            ),
            useCenter=false)
            drawArc(
                Color(red=0, green =255,blue=0),
                startAngle = -90F,
                sweepAngle = progress*360,
                style=Stroke(
                    cap=StrokeCap.Round,
                    width = 6F
                ),
                useCenter=false)

        }
        Text(text =( progress*10).toString(),
            modifier = modifier.align(Alignment.Center),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp
            )
        }
}
@Preview(showBackground = true)
@Composable
fun UserScoreProgressBarPreview(){
    UserScoreProgressBar(
        progress = 0.5F
        )
}