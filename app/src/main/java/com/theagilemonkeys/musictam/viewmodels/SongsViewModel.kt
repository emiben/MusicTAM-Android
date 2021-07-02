package com.theagilemonkeys.musictam.viewmodels

import com.theagilemonkeys.musictam.models.Album
import com.theagilemonkeys.musictam.models.Song
import com.theagilemonkeys.musictam.network.repositories.Result
import com.theagilemonkeys.musictam.network.repositories.SongsRepository
import com.theagilemonkeys.musictam.utils.BaseViewModel
import com.theagilemonkeys.musictam.utils.Resource
import com.theagilemonkeys.musictam.utils.Status
import kotlinx.coroutines.CoroutineDispatcher

class SongsViewModel(
    private val repository: SongsRepository,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<List<Song>>(dispatcher) {

    override suspend fun apiCall(vararg args: String): Resource<List<Song>> {
        val response: Resource<List<Song>>
        if(args[0] == "null") return getNoSongsError()
        val result = repository.getSongs(args[0].toInt())

        response = if (result is Result.Success) {
            if(result.data.resultCount == 0){
                getNoSongsError()
            } else {
                val data = result.data.results
                Resource(Status.SUCCESS, data, "")
            }
        } else {
            Resource(Status.ERROR, null, (result as Result.Error).exception.message)
        }

        return response
    }

    private fun getNoSongsError() : Resource<List<Song>> {
        return Resource(Status.ERROR, null, "Seems like we couldn't find any song for this album")
    }
}