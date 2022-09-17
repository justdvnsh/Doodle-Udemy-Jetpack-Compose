package divyansh.tech.doodle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import divyansh.tech.doodle.ui.theme.DoodleTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoodleTheme {

                val viewModel = MainViewModel()

                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState =
                    BottomSheetState(BottomSheetValue.Collapsed)
                )

                BoxWithConstraints {
                    BottomSheetScaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Doodle")
                                },
                                actions = {
                                    IconButton(onClick = { viewModel.deleteDrawing() }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                                    }
                                }
                            )
                        },
                        sheetElevation = 4.dp,
                        sheetContent = {
                            BottomSheetContent(
                                viewModel = viewModel,
                                onColorSelected = {
                                    viewModel.onColorSelect(it)
                                    viewModel.addPath()
                                },
                                onStrokeSelected = {
                                    viewModel.onStrokeWidthSelect(it)
                                    viewModel.addPath()
                                }
                            )
                        },
                        sheetBackgroundColor = MaterialTheme.colors.background,
                        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        scaffoldState = bottomSheetScaffoldState
                    ) {
                        DrawingArea(
                            path = viewModel.pathList,
                            color = viewModel.selectedColor,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 40.dp),
                            stroke = viewModel.selectedStrokeWidth
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    viewModel: MainViewModel,
    onColorSelected: (selectedColor: Color) -> Unit = {},
    onStrokeSelected: (selectedStrokeWidth: Dp) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Text(text = "Colors", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyRow(
            contentPadding = PaddingValues(16.dp),
        ) {
            items(viewModel.colorList) { item ->
                Box(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
                    .background(
                        color = item,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clickable(onClick = { onColorSelected(item) })
                )
            }
        }

        Text(text = "Stroke Width", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyRow(
            contentPadding = PaddingValues(24.dp)
        ) {
            items(viewModel.strokeWidthList) { item ->
                Box(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(item)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(50.dp)
                    )
                    .clickable(onClick = { onStrokeSelected(item) })
                )
            }
        }
    }
}