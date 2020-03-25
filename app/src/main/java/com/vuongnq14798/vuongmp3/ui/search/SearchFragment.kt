package com.vuongnq14798.vuongmp3.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.SearchView
import com.vuongnq1407.mediaplayer.ui.search.TrackAdapter
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
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(),
    SearchContract.View,
    SearchView.OnQueryTextListener,
    TrackAdapter.OnTrackClickListener {

    private val tracksRepository: TracksRepository by lazy {
        TracksRepository(
            TracksRemoteDataSource.getInstance(),
            TracksLocalDataSource.getInstance(requireContext())
        )
    }
    private val searchPresenter: SearchPresenter by lazy {
        SearchPresenter(
            tracksRepository,
            this
        )
    }

    private val tracks = mutableListOf<Track>()
    override val layoutResId: Int = R.layout.fragment_search

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initComponents() {
        searchView.apply {
            isActivated = true
            onActionViewExpanded()
            clearFocus()
            setOnQueryTextListener(this@SearchFragment)
        }

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

    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean = run {
        Handler().post { newText?.let { searchPresenter.searchTracks(it) } }
        true
    }

    override fun showTracks(tracks: List<Track>) {
        this.tracks.apply {
            clear()
            addAll(tracks)
        }
        val trackAdapter = TrackAdapter(TrackDiffCallBack(), this)
        searchRecycler.adapter = trackAdapter
        trackAdapter.submitList(this.tracks)
    }

    override fun showError(exception: Exception) {
        requireActivity().toast(getString(R.string.error_message))
    }

    override fun onTrackClicked(track: Track) {
        (activity as MainActivity).getMediaPlayerService()?.changeTrack(track)
        startActivity(PlayMusicActivity.getIntent(requireContext()))
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, SearchFragment::class.java)
    }
}