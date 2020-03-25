package com.vuongnq14798.vuongmp3.util

import com.vuongnq14798.vuongmp3.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    fun initGenreUrl(genre: String) =
        String.format(Constants.Url.BASE_URL_V2.plus(Constants.Url.BASE_GENRE_URL), genre, BuildConfig.CLIENT_ID)

    fun initSearchUrl(searchKey: String) =
        String.format(Constants.Url.BASE_URL.plus(Constants.Url.BASE_SEARCH_URL), searchKey, BuildConfig.CLIENT_ID)

    fun initDownloadUrl(trackId: String) =
        String.format(Constants.Url.BASE_URL.plus(Constants.Url.BASE_DOWNLOAD_URL), trackId, BuildConfig.CLIENT_ID)

    fun initStreamUrl(trackId: String) =
        String.format(Constants.Url.BASE_URL.plus(Constants.Url.BASE_STREAM_URL), trackId, BuildConfig.CLIENT_ID)

    fun formatTime(time: Int): String {
        val format = SimpleDateFormat("mm:ss", Locale.getDefault())
        return format.format(time)
    }

    fun initFileName(fileName: String?) =
        String.format(Constants.Track.FILE_NAME, fileName)
}
