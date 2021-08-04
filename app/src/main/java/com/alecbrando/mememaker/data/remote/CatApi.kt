package com.alecbrando.mememaker.data.remote

import com.alecbrando.mememaker.data.models.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApi {
    @GET("cat" )
    suspend fun getCatImage(
        @Query("filter") filter: String?,
        @Query("json") json: Boolean = true,

        ): Response<Cat>

    @GET("cat/says/{text}" )
    suspend fun getCatImageWithText(
        @Path("text") text: String,
        @Query("filter") filter: String?,
        @Query("json") json: Boolean = true,
        @Query("size") size: Int?,
        @Query("color") color: String?,

        ): Response<Cat>

    @GET("cat/gif" )
    suspend fun getCatGif(
        @Query("filter") filter: String?,
        @Query("json") json: Boolean = true,

        ): Response<Cat>

    @GET("cat/gif/says/{text}" )
    suspend fun getCatGifWithText(
        @Path("text") text: String,
        @Query("filter") filter: String?,
        @Query("json") json: Boolean = true,
        @Query("size") size: Int?,
        @Query("color") color: String?,

        ): Response<Cat>
}