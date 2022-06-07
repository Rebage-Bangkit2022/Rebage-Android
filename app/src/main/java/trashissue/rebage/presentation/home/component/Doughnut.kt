package trashissue.rebage.presentation.home.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

data class ChartData(
    val name: String,
    val color: Color,
    val value: Double
)

private enum class AnimatedCircleProgress {
    START,
    END
}

@Composable
fun Doughnut(
    modifier: Modifier = Modifier,
    width: Dp,
    data: List<ChartData>
) {
    val total = remember(data) { data.sumOf { it.value } }
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply { targetState = AnimatedCircleProgress.END }
    }
    val stroke = with(LocalDensity.current) { Stroke(width.toPx()) }
    val transition = updateTransition(currentState, label = "doughnut")
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = LinearOutSlowInEasing
            )
        },
        label = ""
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }
    val shift by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
            )
        },
        label = "doughnut"
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            30f
        }
    }

    Canvas(modifier = modifier) {
        var startAngle = shift - 90
        var sweepAngle: Float
        val innerRadius = (size.minDimension - stroke.width)
        val topLeft = Offset(stroke.width / 2, stroke.width / 2)

        for (garbage in data) {
            sweepAngle = ((garbage.value / total.toFloat()) * angleOffset).toFloat()

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
