package com.vuongnq14798.vuongmp3.data.source

import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.local.TracksLocalDataSource
import com.vuongnq14798.vuongmp3.data.source.remote.TracksRemoteDataSource
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class TracksRepository(
    private val remoteDataSource: TracksDataSource.Remote,
    private val localDataSource: TracksDataSource.Local
) : TracksDataSource.Remote, TracksDataSource.Local {

    override fun getTracksRemote(
        genre: String,
        callback: OnDataLoadedListener<List<Track>>
    ) {
        remoteDataSource.getTracksRemote(genre, callback)
    }

    override fun searchTracksRemote(
        searchKey: String,
        callback: OnDataLoadedListener<List<Track>>
    ) {
        remoteDataSource.searchTracksRemote(searchKey, callback)
    }

    override fun getGenres(callback: OnDataLoadedListener<List<Genre>>) {
        localDataSource.getGenres(callback)
    }

    override fun getTracksLocal(callback: OnDataLoadedListener<List<Track>>) {
        localDataSource.getTracksLocal(callback)
    }

    override fun getTrackDownloaded(callback: OnDataLoadedListener<List<Track>>) {
        localDataSource.getTrackDownloaded(callback)
    }

    companion object {
        private var INSTANCE: TracksRepository? = null
        fun getInstance(
            remoteDataSource: TracksDataSource.Remote,
            localDataSource: TracksDataSource.Local
        ) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TracksRepository(remoteDataSource, localDataSource).also { INSTANCE = it }
        }
    }
}
