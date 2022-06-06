package trashissue.rebage.presentation.threers.component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import trashissue.rebage.R

private enum class SwipingState {
    EXPANDED,
    COLLAPSED
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMotionApi::class)
@Composable
fun SwipeableContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .systemBarsPadding()
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val motionScene = rememberMotionScene()
        val componentHeight = remember { with(density) { maxHeight.toPx() } }
        val swipeableState = rememberSwipeableState(SwipingState.EXPANDED)
        val anchors = mapOf(
            0f to SwipingState.COLLAPSED,
            componentHeight to SwipingState.EXPANDED,
        )
        val progress = if (swipeableState.progress.to == SwipingState.COLLAPSED) {
            swipeableState.progress.fraction
        } else {
            1f - swipeableState.progress.fraction
        }
        val connection = remember {
            object : NestedScrollConnection {

                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val delta = available.y

                    return if (delta < 0) {
                        swipeableState.performDrag(delta).toOffset()
                    } else {
                        Offset.Zero
                    }
                }

                override fun onPostScroll(
                    consumed: Offset,
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    return if (source == NestedScrollSource.Fling) {
                        if (consumed.y == 0f) {
                            val delta = available.y
                            swipeableState.performDrag(delta).toOffset()
                        } else {
                            Offset.Zero
                        }
                    } else {
                        val delta = available.y
                        return swipeableState.performDrag(delta).toOffset()
                    }
                }

                override suspend fun onPreFling(available: Velocity): Velocity {
                    val delta = available.y
                    return if (swipeableState.currentValue != SwipingState.COLLAPSED && delta < 0) {
                        swipeableState.performFling(delta)
                        available
                    } else {
                        super.onPreFling(available)
                    }
                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    swipeableState.performFling(velocity = available.y)
                    return super.onPostFling(consumed, available)
                }

                private fun Float.toOffset() = Offset(0F, this)
            }
        }

        MotionLayout(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.6F) },
                    orientation = Orientation.Vertical
                )
                .nestedScroll(connection),
            motionScene = MotionScene(content = motionScene),
            progress = progress
        ) {
            content()
        }
    }
}

@Composable
private fun rememberMotionScene(): String {
    val context = LocalContext.current

    return remember {
        context.resources.openRawResource(R.raw.threers_scene).readBytes().decodeToString()
    }
}
