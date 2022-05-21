package trashissue.rebage.presentation.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import timber.log.Timber
import trashissue.rebage.presentation.home.GarbageStat

data class ChartData(
    val name: String,
    val color: Color,
    val value: Double
)

@Composable
fun Doughnut(
    modifier: Modifier = Modifier,
    width: Dp,
    data: List<ChartData>
) {

    val total = remember(data) { data.sumOf { it.value } }
    val density = LocalDensity.current


    Canvas(modifier = modifier.size(width)) {
        var startAngle = 0F
        var sweepAngle: Float
        val widthPx = with(density) { width.toPx() }
        val stroke = Stroke(width = widthPx)
        val innerRadius = (size.minDimension - stroke.width)
        val topLeft = Offset(stroke.width / 2, stroke.width / 2)

        for (garbage in data) {
            sweepAngle = ((garbage.value / total.toFloat()) * 360.0).toFloat()

            drawArc(
                color = garbage.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                size = Size(innerRadius, innerRadius),
                style = stroke,
                topLeft = topLeft
            )

            startAngle += sweepAngle
        }
    }
}
