package com.vuongnq14798.vuongmp3.ui.home

import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.ui.OnDataLoadedListener

class HomePresenter(
    private val tracksRepository: TracksRepository,
    private val view: HomeContract.View
) : HomeContract.Presenter {

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

    override fun getGenres() {
        tracksRepository.getGenres(object : OnDataLoadedListener<List<Genre>> {
            override fun onSuccess(data: List<Genre>) {
                view.showGenres(data)
            }

            override fun onFailed(exception: Exception) {
                view.showError(exception)
            }

        })
    }
}
