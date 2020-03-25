package com.vuongnq14798.vuongmp3.ui.detailgenre

import com.vuongnq14798.vuongmp3.data.model.Track
import java.lang.Exception

class DetailGenreContract {
    interface View {

        fun showTracks(tracks: List<Track>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getTracksRemote(genre: String)
    }
}
