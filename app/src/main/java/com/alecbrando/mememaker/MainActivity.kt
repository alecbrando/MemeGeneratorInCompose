package com.alecbrando.mememaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.alecbrando.mememaker.navigation.Navigation
import com.alecbrando.mememaker.ui.theme.MemeMakerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navControllerHost = rememberNavController()
            MemeMakerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(navController = navControllerHost)
                }
            }
        }
    }
}


