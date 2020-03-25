package com.vuongnq14798.vuongmp3.data.source.remote

import android.os.AsyncTask
import android.util.Log
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener
import com.vuongnq14798.vuongmp3.util.Constants
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class TracksAsyncTask(private val isSearch: Boolean,private val callback: OnDataLoadedListener<List<Track>>) :
    AsyncTask<String, Track, List<Track>>() {

    override fun doInBackground(vararg params: String?): List<Track> {

        val url = URL(params[0])
        val stringBuilder = StringBuilder()
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream = BufferedInputStream(urlConnection.inputStream)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            bufferedReader.forEachLine { stringBuilder.append(it) }
            inputStream.close()
        } catch (exception: Exception) {
            callback.onFailed(exception)
        } finally {
            urlConnection.disconnect()
        }
        return toListTracks(stringBuilder.toString(), isSearch)
    }

    override fun onPostExecute(result: List<Track>) {

        super.onPostExecute(result)
        callback.onSuccess(result)
    }

    private fun toListTracks(input: String, isSearch: Boolean): List<Track> {
        val tracks = arrayListOf<Track>()
        try {
            if (isSearch) {
                val collection =  JSONArray(input)
                for (i in 0 until collection.length()) {
                    val track = Track(collection.getJSONObject(i))
                    tracks.add(track)
                }
            } else {
                val collection = JSONObject(input).getJSONArray(Constants.Track.COLLECTION)
                for (i in 0 until collection.length()) {
                    val track = Track(collection.getJSONObject(i).getJSONObject(Constants.Track.TRACK))
                    tracks.add(track)
                }

            }
        } catch (exception: Exception) {
            callback.onFailed(exception)
        }
        return tracks
    }
}
