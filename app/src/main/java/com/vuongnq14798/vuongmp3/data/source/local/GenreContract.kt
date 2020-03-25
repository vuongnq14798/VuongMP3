package com.vuongnq14798.vuongmp3.data.source.local

import android.provider.BaseColumns

object GenreContract {

    object GenreEntry : BaseColumns {
        const val TABLE_NAME = "genre"
        const val COLUMN_NAME_NAME = "genreName"
        const val COLUMN_NAME_IMGAGE = "genreImage"
        const val COLUMN_URL_GENRE = "genreURL"
    }
}
