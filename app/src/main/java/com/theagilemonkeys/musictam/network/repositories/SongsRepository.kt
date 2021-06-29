package com.theagilemonkeys.musictam.network.repositories

import com.theagilemonkeys.musictam.models.Song
import com.theagilemonkeys.musictam.models.Response
import com.theagilemonkeys.musictam.network.services.SongsService

interface SongsRepositoryInterface {
    suspend fun getSongs(collectionId: Int): Result<Response<Song>>
}

/**
 * [com.theagilemonkeys.musictam.network.services.SongsService] instance can be provided by a
 * [com.theagilemonkeys.musictam.network.clients.MusicClient] instance.
 */
class SongsRepository(private val songsService: SongsService) : SongsRepositoryInterface,
    BaseRepository() {
    override suspend fun getSongs(collectionId: Int): Result<Response<Song>> =
        safeCall { songsService.getSongs(collectionId) }
}