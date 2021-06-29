package com.theagilemonkeys.musictam.network.clients

import com.theagilemonkeys.musictam.network.services.AlbumsService
import com.theagilemonkeys.musictam.network.services.ArtistsService
import com.theagilemonkeys.musictam.network.services.SongsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MusicClient {
    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val artistsService: ArtistsService = retrofit.create(ArtistsService::class.java)
    val albumsService: AlbumsService = retrofit.create(AlbumsService::class.java)
    val songsService: SongsService = retrofit.create(SongsService::class.java)
}