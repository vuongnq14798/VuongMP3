package com.vuongnq14798.vuongmp3.ui.downloaded

import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class DownloadedPresenter(
    private val tracksRepository: TracksRepository,
    private val view: DownloadedContract.View
) : DownloadedContract.Presenter {

    override fun getTrackDownloaded() {
        tracksRepository.getTrackDownloaded(object : OnDataLoadedListener<List<Track>> {

            override fun onSuccess(data: List<Track>) {
                view.showTracks(data)
            }

            override fun onFailed(exception: Exception) {
                view.showError(exception)
            }
        })
    }
}
