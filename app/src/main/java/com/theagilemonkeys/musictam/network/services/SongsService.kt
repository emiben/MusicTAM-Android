package com.theagilemonkeys.musictam.network.services

import com.theagilemonkeys.musictam.models.Response
import com.theagilemonkeys.musictam.models.Song
import retrofit2.http.GET
import retrofit2.http.Query

interface SongsService {
    @GET("lookup?entity=song")
    suspend fun getSongs(@Query("id") collectionId: String): Response<Song>
}