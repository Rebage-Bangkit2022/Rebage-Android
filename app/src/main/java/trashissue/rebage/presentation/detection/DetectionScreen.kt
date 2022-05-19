package trashissue.rebage.presentation.detection

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import trashissue.rebage.domain.model.Result
import trashissue.rebage.presentation.camera.CameraActivity
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetectionScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopAppBar() {
                Text(
                    text = "Deteksi",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            var imageFile by rememberSaveable { mutableStateOf<File?>(null) }
            val imageBitmap by fileAsImageBitmap(imageFile)
            val cameraLauncher = rememberCameraLauncher { imageFile = it }
            val context = LocalContext.current
            val systemUiController = rememberSystemUiController()

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                cameraLauncher.launch(Intent(context, CameraActivity::class.java))
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Camera,
                                contentDescription = "Scan"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Scan")
                        }
                    }
                }
                items(10) {
                    ScannedItem()
                }
                item {
                    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
                    var expanded by remember { mutableStateOf(false) }
                    var selectedOptionText by remember { mutableStateOf(options[0]) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedOptionText,
                            onValueChange = { },
                            label = { Text("Label") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            options.forEach { selectionOption ->
                                DropdownMenuItem(
                                    onClick = {
                                        selectedOptionText = selectionOption
                                        expanded = false
                                    }
                                ) {
                                    Text(text = selectionOption)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ScannedItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(100.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
        )
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = "Plastik",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.subtitle1
                )
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Deteksi",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.caption
                )
                Counter()
            }
        }
    }
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null
        )
        Text(
            text = "3",
            style = MaterialTheme.typography.caption
        )
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = null
        )
    }
}

@Composable
fun rememberCameraLauncher(
    onFailed: () -> Unit = { },
    onSuccess: (File) -> Unit = { }
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode != CameraActivity.RESULT_SUCCESS) {
                onFailed()
                return@rememberLauncherForActivityResult
            }

            val data = result.data
            val imageFile = data?.getSerializableExtra(CameraActivity.KEY_IMAGE_RESULT) as File
            onSuccess(imageFile)
        }
    )
}

@Composable
fun fileAsImageBitmap(file: File?): State<Result<ImageBitmap>> {
    val initialValue: Result<ImageBitmap> = Result.NoData()

    return produceState(initialValue, file) {
        value = try {
            val imageBitmap = BitmapFactory.decodeFile((file ?: return@produceState).absolutePath)
            Result.Success(imageBitmap.asImageBitmap())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
