package com.vuongnq14798.vuongmp3.data.source.remote

import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksDataSource
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener
import com.vuongnq14798.vuongmp3.util.StringUtils

class TracksRemoteDataSource : TracksDataSource.Remote {

    override fun getTracksRemote(
        genre: String,
        callback: OnDataLoadedListener<List<Track>>
    ) {
        TracksAsyncTask(false, callback).execute(StringUtils.initGenreUrl(genre))
    }

    override fun searchTracksRemote(
        searchKey: String,
        callback: OnDataLoadedListener<List<Track>>
    ) {
        TracksAsyncTask(true, callback).execute(StringUtils.initSearchUrl(searchKey))
    }

    companion object {

        private var INSTANCE: TracksRemoteDataSource? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TracksRemoteDataSource().also { INSTANCE = it }
        }
    }
}
