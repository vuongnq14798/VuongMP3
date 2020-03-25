package com.vuongnq14798.vuongmp3.data.source

import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

interface TracksDataSource {

    interface Remote {

        fun getTracksRemote(
            genre: String,
            callback: OnDataLoadedListener<List<Track>>
        )

        fun searchTracksRemote(
            searchKey: String,
            callback: OnDataLoadedListener<List<Track>>
        )
    }

    interface Local {
        fun getGenres(callback: OnDataLoadedListener<List<Genre>>)
        fun getTracksLocal(callback: OnDataLoadedListener<List<Track>>)
        fun getTrackDownloaded(callback: OnDataLoadedListener<List<Track>>)
    }
}
