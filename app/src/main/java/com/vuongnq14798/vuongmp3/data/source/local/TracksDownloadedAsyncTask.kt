package com.vuongnq14798.vuongmp3.data.source.local

import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener
import com.vuongnq14798.vuongmp3.util.Constants
import java.io.File
import java.io.FilenameFilter
import java.lang.IllegalStateException


class TracksDownloadedAsyncTask(
    private val callback: OnDataLoadedListener<List<Track>>
) : AsyncTask<String, Track, List<Track>>() {

    override fun doInBackground(vararg params: String?): List<Track> {

        val tracks = mutableListOf<Track>()
        val file = File(Constants.Track.PATH_NAME)

        try {
            if (file.listFiles(FileExtensionFilter()).isNotEmpty()) {
                for (f in file.listFiles(FileExtensionFilter())) {
                    val track = Track(f)
                    tracks.add(track)
                }
            }
        } catch (e: IllegalStateException) {
            callback.onFailed(e)
        }
        return tracks
    }

    override fun onPostExecute(result: List<Track>) {

        super.onPostExecute(result)
        callback.onSuccess(result)
    }

    class FileExtensionFilter : FilenameFilter {
        override fun accept(dir: File?, name: String): Boolean {
            return name.endsWith(".mp3") || name.endsWith(".MP3")
        }
    }
}
