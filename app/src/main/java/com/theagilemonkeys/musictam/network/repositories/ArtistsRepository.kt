package com.theagilemonkeys.musictam.network.repositories

import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.models.Response
import com.theagilemonkeys.musictam.network.services.ArtistsService

interface ArtistsRepositoryInterface {
    suspend fun getArtists(searchTerm: String): Result<Response<Artist>>
}

/**
 * [com.theagilemonkeys.musictam.network.services.ArtistsService] instance can be provided by a
 * [com.theagilemonkeys.musictam.network.clients.MusicClient] instance.
 */
class ArtistsRepository(private val artistsService: ArtistsService) : ArtistsRepositoryInterface,
    BaseRepository() {
    override suspend fun getArtists(searchTerm: String): Result<Response<Artist>> =
        safeCall { artistsService.getArtists(searchTerm) }
}