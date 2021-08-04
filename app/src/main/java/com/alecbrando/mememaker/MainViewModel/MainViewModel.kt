package com.alecbrando.mememaker.MainViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alecbrando.mememaker.data.models.Cat
import com.alecbrando.mememaker.data.repo.Repository
import com.alecbrando.mememaker.util.Resource
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(
   private val repository: Repository
) : ViewModel() {

    private val _text = mutableStateOf("")
    val text : State<String> get() = _text

    val filterOptions = listOf("None", "Blur", "Mono", "Sepia", "Negative", "Paint", "Pixel")

    private val _filter = mutableStateOf(filterOptions[0])
    val filter : State<String> get() = _filter
    private val _media = mutableStateOf<Media>(Media.IMAGE)
    val media : State<Media> get() = _media


    private var _catImage = MutableLiveData<Resource<Cat>>()
    val catImage: LiveData<Resource<Cat>> get() = _catImage


    fun setText(text: String) {
        _text.value = text
    }

    fun setFilter(filter: String) {
        _filter.value = filter
    }

    fun setMedia(media: Media) {
        _media.value = media
    }


    fun getData() {
        Log.d("VALUES", text.value)
        Log.d("VALUES", filter.value)
        Log.d("VALUES", media.value.toString())
        _catImage.value = Resource.Loading(null)
        viewModelScope.launch {
            when (_media.value) {
                Media.IMAGE -> {
                    if (text.value.isEmpty()) {
                        _catImage.value = repository.getCatImage(filter.value)
                    } else {
                        _catImage.value = repository.getCatImageWithText(
                            text.value,
                            filter.value,
                            text.value.length,
                            color = "White"
                        )
                    }
                }
                Media.GIF -> {
                    if (text.value.isEmpty()) {
                        _catImage.value = repository.getCatGif(filter.value)
                    } else {
                        _catImage.value = repository.getCatGifWithText(
                            text.value,
                            filter.value,
                            size = text.value.length,
                            color = "White"
                        )
                    }
                }
            }
        }
    }
}




enum class Media {
    GIF,
    IMAGE
}