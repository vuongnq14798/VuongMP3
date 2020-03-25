package com.vuongnq14798.vuongmp3.data.source.local

import android.content.Context
import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksDataSource
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class TracksLocalDataSource(private val context: Context) : TracksDataSource.Local {

    override fun getGenres(callback: OnDataLoadedListener<List<Genre>>) {

        var listGenre = arrayListOf<Genre>()

        try {
            val dbHelper = DbHelper(context)
            listGenre = dbHelper.getAllGenre() as ArrayList<Genre>
        } catch (exception: Exception) {
            callback.onFailed(exception)
        }

        callback.onSuccess(listGenre)
    }

    override fun getTracksLocal(callback: OnDataLoadedListener<List<Track>>) {
        TracksLocalAsyncTask(context, callback).execute()
    }

    override fun getTrackDownloaded(callback: OnDataLoadedListener<List<Track>>) {
        TracksDownloadedAsyncTask(callback).execute()
    }

    companion object {

        private var INSTANCE: TracksLocalDataSource? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: TracksLocalDataSource(context).also { INSTANCE = it }
        }
    }
}
