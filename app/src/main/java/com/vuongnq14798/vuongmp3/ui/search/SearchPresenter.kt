package com.vuongnq14798.vuongmp3.ui.search

import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class SearchPresenter(
    private val tracksRepository: TracksRepository,
    private val view: SearchContract.View
) : SearchContract.Presenter {

    override fun searchTracks(query: String) {
        tracksRepository.searchTracksRemote(query, object :
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
