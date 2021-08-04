package com.alecbrando.mememaker.data.repo

import com.alecbrando.mememaker.data.models.Cat
import com.alecbrando.mememaker.data.remote.CatApi
import com.alecbrando.mememaker.util.Resource
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class Repository(
    private val api: CatApi,
) {

    suspend fun getCatImage(filter: String): Resource<Cat> {
        return try {
            val res = api.getCatImage(filter = filter)
            if (res.isSuccessful) {
                Resource.Success(res.body()!!)
            } else {
                Resource.Error(null, "Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage!!)
        }
    }

    suspend fun getCatImageWithText(
        filter: String,
        text: String,
        size: Int?,
        color: String
    ): Resource<Cat> {
        return try {
            val res = api.getCatImageWithText(filter = filter, text = text, size = size, color = color)
            if (res.isSuccessful) {
                Resource.Success(res.body()!!)
            } else {
                Resource.Error(null, "Something went wrong")
            }

        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage!!)
        }
    }

    suspend fun getCatGif(filter: String): Resource<Cat> {
        return try {
            val res = api.getCatGif(filter = filter)
            if (res.isSuccessful) {
                Resource.Success(res.body()!!)
            } else {
                Resource.Error(null, "Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage!!)
        }
    }

    suspend fun getCatGifWithText(
        filter: String,
        text: String,
        color: String,
        size: Int?
    ): Resource<Cat> {
        return try {
            val res =
                api.getCatGifWithText(filter = filter, text = text, color = color, size = size)
            if (res.isSuccessful) {
                Resource.Success(res.body()!!)
            } else {
                Resource.Error(null, "Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error(null, e.localizedMessage!!)
        }
    }

}