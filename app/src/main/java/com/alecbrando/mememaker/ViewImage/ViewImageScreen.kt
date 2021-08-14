package com.alecbrando.mememaker.ViewImage

import android.graphics.Insets.add
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.util.Log
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
import android.content.Intent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import coil.compose.ImagePainter.State.Empty.painter
import java.io.File


@ExperimentalCoilApi
@Composable
fun ViewImageScreen(
    navController: NavController,
    extension : String
) {
    val context = LocalContext.current
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

    Log.d("URL", extension)
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
    val shareIntent = Intent(Intent.ACTION_SEND);
    shareIntent.type = "text/plain";
    shareIntent.putExtra(Intent.EXTRA_TEXT, BASE_URL + extension);


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
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        if (!loading) {
            Button(onClick = {
                context.startActivity(Intent.createChooser(shareIntent, "Share link using"))
            },
               
            ) {
                Text(
                    text = "Share"
                )
            }
        }

    }
}