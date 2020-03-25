package com.vuongnq14798.vuongmp3.data.model

import android.database.Cursor
import android.os.Parcelable
import android.provider.BaseColumns
import android.provider.MediaStore
import com.vuongnq14798.vuongmp3.util.Constants
import com.vuongnq14798.vuongmp3.util.StringUtils
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject
import java.io.File

@Parcelize
data class Track(
    val id: Int,
    val title: String,
    val duration: Int?,
    val downloadable: Boolean,
    val streamUrl: String,
    val username: String,
    val artworkUrl: String?,
    val downloadUrl: String?,
    val artist: String?,
    val description: String?,
    val isOnline: Boolean = true
) : Parcelable {

    constructor(jsonObject: JSONObject) : this(
        id = jsonObject.getInt(Constants.Track.ID),
        title = jsonObject.getString(Constants.Track.TITLE),
        duration = jsonObject.getInt(Constants.Track.DURATION),
        downloadable = jsonObject.getBoolean(Constants.Track.IS_DOWNLOAD),
        streamUrl = StringUtils.initStreamUrl(jsonObject.getInt(Constants.Track.ID).toString()),
        username = jsonObject.getJSONObject(Constants.Track.USER).getString(Constants.Track.USERNAME),
        artworkUrl = jsonObject.getString(Constants.Track.ARTWORK_URL),
        downloadUrl = jsonObject.optString(Constants.Track.DOWNLOAD_URL, null),
        artist = if (!jsonObject.isNull(Constants.Track.PUBLISHER_METADATA)
            && !jsonObject.getJSONObject(Constants.Track.PUBLISHER_METADATA)
                .isNull(Constants.Track.ARTIST)
        ) {
            jsonObject.getJSONObject(Constants.Track.PUBLISHER_METADATA)
                .getString(Constants.Track.ARTIST)
        } else {
            jsonObject.getJSONObject(Constants.Track.USER).getString(Constants.Track.USERNAME)
        },
        description = jsonObject.getString(Constants.Track.DESCRIPTION),
        isOnline = true
    )

    constructor(cursor: Cursor) : this(
        id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)),
        title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
        duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
        downloadable = true,
        streamUrl = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
        username = "",
        artworkUrl = "",
        downloadUrl = "",
        artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
        description = "",
        isOnline = false
    )

    constructor(file: File) : this(
        id = 0,
        title = file.nameWithoutExtension,
        duration = null,
        downloadable = true,
        streamUrl = file.path,
        username = "",
        artworkUrl = "",
        downloadUrl = "",
        artist = "",
        description = "",
        isOnline = false
    )
}
