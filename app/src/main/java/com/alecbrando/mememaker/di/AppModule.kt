package com.alecbrando.mememaker.di

import com.alecbrando.mememaker.data.remote.CatApi
import com.alecbrando.mememaker.data.repo.Repository
import com.alecbrando.mememaker.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatRepo(
        api: CatApi
    ): Repository = Repository(api)

    @Provides
    @Singleton
    fun provideCatApiManager(): CatApi = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build()
        .create(CatApi::class.java)

}