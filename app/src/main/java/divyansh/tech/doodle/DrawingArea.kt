package divyansh.tech.doodle

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PathState(
    val path: Path = Path(),
    val color: Color = Color.Black,
    val strokeWidth: Dp = 6.dp
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawingArea(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    stroke: Dp = 6.dp,
    onPathDrawn: (path: PathState) -> Unit = {},
    path: List<PathState> = listOf()
) {
    val currentPath = Path()
    val movePath = remember{ mutableStateOf<Offset?>(Offset.Zero)}

    Canvas(
        modifier = modifier
            .pointerInteropFilter {
                when(it.action){
                    MotionEvent.ACTION_DOWN ->{
                        Log.e("PATH x -> ", it.x.toString())
                        currentPath.moveTo(it.x,it.y)
                    }
                    MotionEvent.ACTION_MOVE ->{
                        movePath.value = Offset(it.x,it.y)
                    }
                    else ->{
                        onPathDrawn(
                            PathState(
                                path = currentPath,
                                color = color,
                                strokeWidth = stroke
                            )
                        )
                        movePath.value = null
                    }
                }
                true
            }
    ) {
        movePath.value?.let {
            currentPath.lineTo(it.x,it.y)
            drawPath(
                path = currentPath,
                color = color,
                style = Stroke(
                    width = stroke.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        path.forEach {
            drawPath(
                path = it.path,
                color = it.color,
                style  = Stroke(
                    width = it.strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
    }
}