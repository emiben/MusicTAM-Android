package com.theagilemonkeys.musictam.network.repositories

import java.io.IOException

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

open class BaseRepository {
    suspend fun <T : Any> safeCall(call: suspend () -> T): Result<T> = try {
        Result.Success(call())
    } catch (e: Exception) {
        Result.Error(IOException("👎 Error executing call: $e"))
    }
}