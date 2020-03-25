package com.vuongnq14798.vuongmp3.ui.mymusic

import com.vuongnq14798.vuongmp3.data.model.Track

interface MyMusicContract {
    interface View {

        fun showTracks(tracks: List<Track>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getTracksLocal ()
    }
}
