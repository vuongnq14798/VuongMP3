package com.vuongnq14798.vuongmp3.ui.home

import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.data.model.Track
import java.lang.Exception

interface HomeContract {

    interface View {

        fun showGenres(genres: List<Genre>)
        fun showTracks(tracks: List<Track>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getTracksRemote (genre: String)
        fun getGenres()
    }
}
