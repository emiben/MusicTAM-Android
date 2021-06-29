package com.theagilemonkeys.musictam.network.repositories

import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.models.Response
import com.theagilemonkeys.musictam.network.services.AlbumsService

interface AlbumsRepositoryInterface {
    suspend fun getAlbums(artistId: String): Result<Response<Album>>
}


/**
 * [com.theagilemonkeys.musictam.network.services.AlbumsService] instance can be provided by a
 * [com.theagilemonkeys.musictam.network.clients.MusicClient] instance.
 */
class AlbumsRepository(private val albumsService: AlbumsService) : AlbumsRepositoryInterface,
    BaseRepository() {
    override suspend fun getAlbums(artistId: String): Result<Response<Album>> =
        safeCall { albumsService.getAlbums(artistId) }
}