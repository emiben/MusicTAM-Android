package com.theagilemonkeys.musictam.network.services

import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsService {
    @GET("lookup?entity=album")
    suspend fun getAlbums(@Query("amgArtistId") amgArtistId: Int): Response<Album>
}