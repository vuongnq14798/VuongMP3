package com.vuongnq14798.vuongmp3.ui.downloaded

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
import com.vuongnq14798.vuongmp3.util.TrackDiffCallBack
import com.vuongnq14798.vuongmp3.util.ContextExtensions.toast
import kotlinx.android.synthetic.main.fragment_my_music.*

class DownloadedFragment : BaseFragment(),
    DownloadedContract.View,
    TrackAdapter.OnTrackClickListener {
    private val tracksRepository: TracksRepository by lazy {
        TracksRepository(
            TracksRemoteDataSource.getInstance(),
            TracksLocalDataSource.getInstance(context!!)
        )
    }
    private val downloadedPresenter: DownloadedPresenter by lazy { DownloadedPresenter(tracksRepository, this) }

    private val tracks = mutableListOf<Track>()

    override val layoutResId: Int = R.layout.fragment_my_music

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initComponents() {
        downloadedPresenter.getTrackDownloaded()

        fab.setOnClickListener {
            (activity as MainActivity).getMediaPlayerService()?.let {
                if (tracks.isNotEmpty()) {
                    it.updateTracks(tracks)
                    it.changeTrack(tracks[0])
                    startActivity(PlayMusicActivity.getIntent(requireContext()))
                }
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
        //Toast.makeText(context, R.string.error_message, Toast.LENGTH_SHORT).show()
    }

    override fun onTrackClicked(track: Track) {
        startActivity(PlayMusicActivity.getIntent(requireContext()))
        (activity as MainActivity).getMediaPlayerService()?.changeTrack(track)
    }
}
