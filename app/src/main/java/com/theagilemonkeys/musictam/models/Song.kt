package com.theagilemonkeys.musictam.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val wrapperType: String?,
    val trackId: Int?,
    val trackName: String?,
    val previewUrl: String?,
    val artworkUrl100: String?
) : Parcelable