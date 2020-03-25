package com.vuongnq14798.vuongmp3.ui.search

import com.vuongnq14798.vuongmp3.data.model.Track

class SearchContract {
    interface View {

        fun showTracks(tracks: List<Track>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun searchTracks(query: String)
    }
}