package com.vuongnq14798.vuongmp3.ui.mymusic

import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class MyMusicPresenter(
    private val tracksRepository: TracksRepository,
    private val view: MyMusicContract.View
) : MyMusicContract.Presenter {

    override fun getTracksLocal() {
        tracksRepository.getTracksLocal(object : OnDataLoadedListener<List<Track>> {

            override fun onSuccess(data: List<Track>) {
                view.showTracks(data)
            }

            override fun onFailed(exception: Exception) {
                view.showError(exception)
            }
        })
    }
}
