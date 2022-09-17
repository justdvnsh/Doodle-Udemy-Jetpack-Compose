package divyansh.tech.doodle

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _colorList = mutableStateListOf<Color>()
    val colorList: List<Color> = _colorList

    private val _strokeWidthList = mutableStateListOf<Dp>()
    val strokeWidthList: List<Dp> = _strokeWidthList

    private val _pathList = mutableStateListOf<PathState>()
    val pathList: List<PathState> = _pathList

    var selectedColor by mutableStateOf(Color.Black)

    var selectedStrokeWidth by mutableStateOf(6.dp)

    init {
        _colorList.add(Color.Black)
        _colorList.add(Color.Gray)
        _colorList.add(Color.Green)
        _colorList.add(Color.Red)
        _colorList.add(Color.Blue)
        _colorList.add(Color.Yellow)
        _colorList.add(Color.Cyan)
        _colorList.add(Color.Magenta)

        for (i in 6 until 26 step 2) {
            _strokeWidthList.add(i.dp)
        }

        _pathList.add(PathState())
    }

    fun onColorSelect(color: Color) {
        selectedColor = color
    }

    fun onStrokeWidthSelect(width: Dp) {
        selectedStrokeWidth = width
    }

    fun addPath() {
        _pathList.add(
            PathState(
                path = Path(),
                color = selectedColor,
                strokeWidth = selectedStrokeWidth
            )
        )
    }

    fun deleteDrawing() {
        _pathList.clear()
        addPath()
    }
}