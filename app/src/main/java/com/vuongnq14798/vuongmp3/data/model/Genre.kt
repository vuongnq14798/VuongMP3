package com.vuongnq14798.vuongmp3.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre(
    val genreName: String,
    val genreImage: Int,
    val genreURL: String
) : Parcelable
