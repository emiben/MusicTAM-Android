package com.theagilemonkeys.musictam.viewmodels

import com.theagilemonkeys.musictam.models.Artist
import com.theagilemonkeys.musictam.network.repositories.ArtistsRepository
import com.theagilemonkeys.musictam.network.repositories.Result
import com.theagilemonkeys.musictam.utils.BaseViewModel
import com.theagilemonkeys.musictam.utils.Constants.ABC
import com.theagilemonkeys.musictam.utils.Resource
import com.theagilemonkeys.musictam.utils.Status
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import kotlin.streams.asSequence

class SearchArtistsViewModel(
    private val repository: ArtistsRepository,
    dispatcher: CoroutineDispatcher
) : BaseViewModel<List<Artist>>(dispatcher) {

    override suspend fun apiCall(vararg args: String): Resource<List<Artist>> {
        val response: Resource<List<Artist>>
        val result = repository.getArtists(args[0])

        response = if (result is Result.Success) {
            val data = result.data.results
            Resource(Status.SUCCESS, data, "")
        } else {
            Resource(Status.ERROR, null, (result as Result.Error).exception.message)
        }

        return response
    }
}