package trashissue.rebage.presentation.detection

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import trashissue.rebage.domain.model.Result
import trashissue.rebage.presentation.camera.CameraActivity
import trashissue.rebage.presentation.common.statusBarsPaddingWithColor
import trashissue.rebage.presentation.detection.component.AddGarbage
import trashissue.rebage.presentation.detection.component.ScannedGarbage
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetectionScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPaddingWithColor(),
        topBar = {
            TopAppBar {
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            var imageFile by rememberSaveable { mutableStateOf<File?>(null) }
            val imageBitmap by fileAsImageBitmap(imageFile)
            val cameraLauncher = rememberCameraLauncher { imageFile = it }
            val context = LocalContext.current

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(key = { "scan" }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement()
                    ) {
                        Button(
                            onClick = {
                                cameraLauncher.launch(Intent(context, CameraActivity::class.java))
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(top = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt,
                                contentDescription = "Scan"
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Scan")
                        }
                    }
                }
                item(key = { "add_garbage" }) {
                    AddGarbage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }
                items(30, key = { it }) {
                    ScannedGarbage(modifier = Modifier.animateItemPlacement())
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
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
