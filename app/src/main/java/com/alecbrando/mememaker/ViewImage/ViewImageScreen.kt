package com.alecbrando.mememaker.ViewImage

import android.graphics.Insets.add
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.alecbrando.mememaker.util.Constants.BASE_URL
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@Composable
fun ViewImageScreen(
    navController: NavController,
    extension : String
) {

    var loading by remember {
        mutableStateOf(true)
    }

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(LocalContext.current))
            } else {
                add(GifDecoder())
            }
        }
        .build()


    val painter = rememberImagePainter(
        data = BASE_URL + extension,
        builder = {
            crossfade(true)
        },
        imageLoader = imageLoader
    )

    if (painter.state is ImagePainter.State.Success) {
        loading = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            if (loading) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}