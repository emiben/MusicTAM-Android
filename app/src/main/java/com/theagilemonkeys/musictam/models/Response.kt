package com.theagilemonkeys.musictam.models

class Response<T>(
    val resultCount: Int?,
    val results: List<T>
)