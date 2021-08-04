package com.alecbrando.mememaker.Dashboard

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alecbrando.mememaker.MainViewModel.MainViewModel
import com.alecbrando.mememaker.MainViewModel.Media
import com.alecbrando.mememaker.util.Resource
import kotlinx.coroutines.launch


@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current
    var openedMenu by remember {
        mutableStateOf(false)
    }

    var loading by remember {
        mutableStateOf(false)
    }

    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = state,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            if (loading) {
                CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(100.dp))
            TextField(
                label = { Text("Some Dank Meme Text", color = Color.Black) },
                value = viewModel.text.value, onValueChange = {
                    viewModel.setText(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = viewModel.media.value == Media.IMAGE,
                    onClick = {
                        viewModel.setMedia(Media.IMAGE)
                    },
                    colors = RadioButtonDefaults.colors(Color.Red)
                )
                Text(text = "Image", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                RadioButton(
                    selected = viewModel.media.value == Media.GIF,
                    onClick = {
                        viewModel.setMedia(Media.GIF)
                    },
                    colors = RadioButtonDefaults.colors(Color.Blue)
                )
                Text(text = "Gif", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterStart)
                    .border(width = 1.dp, color = Color.LightGray, shape = RectangleShape)
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 5.dp)
            ) {
                Text(text = viewModel.filter.value, fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { openedMenu = !openedMenu }
                )
                DropdownMenu(
                    expanded = openedMenu,
                    onDismissRequest = {
                        openedMenu = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    viewModel.filterOptions.forEachIndexed { index, filterOption ->
                        DropdownMenuItem(onClick = {
                            viewModel.setFilter(filterOption)
                            openedMenu = false
                        }) {
                            Text(filterOption, color = Color.Black)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    viewModel.getData()
                    viewModel.catImage.observe(lifecycle) {
                        when (it) {
                            is Resource.Success -> {
                                loading = false
                                val url = it.data!!.url.substring(1)
                                navController.navigate("viewImage_route?extension=${url}")
                            }
                            is Resource.Error -> {
                                scope.launch {
                                    state.snackbarHostState.showSnackbar(
                                        "Something went wrong",
                                        null,
                                        SnackbarDuration.Long
                                    )
                                }
                            }
                            is Resource.Loading -> {
                                loading = true
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Create Meme")
            }
        }

    }
}