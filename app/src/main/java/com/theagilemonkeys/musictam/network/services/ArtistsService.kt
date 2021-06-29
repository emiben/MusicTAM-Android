package com.theagilemonkeys.musictam.network.services

import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.models.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistsService {
    @GET("search?entity=allArtist")
    suspend fun getArtists(@Query("term") searchTerm: String): Response<Artist>
}