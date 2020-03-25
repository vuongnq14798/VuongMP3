package com.vuongnq14798.vuongmp3.ui.downloaded

import com.vuongnq14798.vuongmp3.data.model.Track

interface DownloadedContract {
    interface View {

        fun showTracks(tracks: List<Track>)
        fun showError(exception: Exception)
    }

    interface Presenter {
        fun getTrackDownloaded ()
    }
}
