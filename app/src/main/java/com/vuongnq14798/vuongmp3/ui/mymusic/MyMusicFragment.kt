package com.vuongnq14798.vuongmp3.ui.mymusic

import android.os.Bundle
import android.widget.Toast
import com.vuongnq14798.vuongmp3.MainActivity
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.base.BaseFragment
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.data.source.local.TracksLocalDataSource
import com.vuongnq14798.vuongmp3.data.source.remote.TracksRemoteDataSource
import com.vuongnq14798.vuongmp3.ui.playmusic.PlayMusicActivity
import com.vuongnq14798.vuongmp3.util.ContextExtensions.toast
import com.vuongnq14798.vuongmp3.util.TrackDiffCallBack
import kotlinx.android.synthetic.main.fragment_my_music.*
import kotlinx.android.synthetic.main.fragment_my_music.fab

class MyMusicFragment : BaseFragment(),
    MyMusicContract.View,
    TrackAdapter.OnTrackClickListener {

    private val tracksRepository: TracksRepository by lazy {
        TracksRepository(
            TracksRemoteDataSource.getInstance(),
            TracksLocalDataSource.getInstance(context!!)
        )
    }
    private val myMusicPresenter: MyMusicPresenter by lazy { MyMusicPresenter(tracksRepository, this) }

    private val tracks = mutableListOf<Track>()

    override val layoutResId: Int = R.layout.fragment_my_music

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initComponents() {
        myMusicPresenter.getTracksLocal()

        fab.setOnClickListener {
            (activity as MainActivity).getMediaPlayerService()?.let {
                it.updateTracks(tracks)
                it.changeTrack(tracks[0])
                startActivity(PlayMusicActivity.getIntent(requireContext()))
            }
        }
    }

    override fun showTracks(tracks: List<Track>) {
        this.tracks.addAll(tracks)
        val trackAdapter = TrackAdapter(TrackDiffCallBack(), this)
        my_music.adapter = trackAdapter
        trackAdapter.submitList(tracks)
    }

    override fun showError(exception: Exception) {
        context?.toast(getString(R.string.error_message))
    }

    override fun onTrackClicked(track: Track) {
        startActivity(PlayMusicActivity.getIntent(requireContext()))
        (activity as MainActivity).getMediaPlayerService()?.changeTrack(track)
    }
}
