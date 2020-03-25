package com.vuongnq14798.vuongmp3.ui.detailgenre

import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class DetailGenrePresenter(
    private val tracksRepository: TracksRepository,
    private val view: DetailGenreContract.View
) : DetailGenreContract.Presenter {

    override fun getTracksRemote(genre: String) {
        tracksRepository.getTracksRemote(genre, object :
            OnDataLoadedListener<List<Track>> {
            override fun onSuccess(data: List<Track>) {
                view.showTracks(data)
            }

            override fun onFailed(exception: Exception) {
                view.showError(exception)
            }
        })
    }
}
