package com.theagilemonkeys.musictam.viewmodels

import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.network.repositories.AlbumsRepository
import com.theagilemonkeys.musictam.network.repositories.ArtistsRepository
import com.theagilemonkeys.musictam.network.repositories.Result
import com.theagilemonkeys.musictam.utils.BaseViewModel
import com.theagilemonkeys.musictam.utils.Constants.ABC
import com.theagilemonkeys.musictam.utils.Resource
import com.theagilemonkeys.musictam.utils.Status
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import kotlin.streams.asSequence

class AlbumsViewModel(
    private val repository: AlbumsRepository,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<List<Album>>(dispatcher) {

    override suspend fun apiCall(vararg args: String): Resource<List<Album>> {
        val response: Resource<List<Album>>
        if(args[0] == "null") return getNoAlbumsError()
        val result = repository.getAlbums(args[0].toInt())

        response = if (result is Result.Success) {
            if(result.data.resultCount == 0){
                getNoAlbumsError()
            } else {
                val data = result.data.results
                Resource(Status.SUCCESS, data, "")
            }
        } else {
            Resource(Status.ERROR, null, (result as Result.Error).exception.message)
        }

        return response
    }

    private fun getNoAlbumsError() : Resource<List<Album>> {
        return Resource(Status.ERROR, null, "Seems like we couldn't find any album for this artist")
    }
}