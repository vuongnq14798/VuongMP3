package com.vuongnq14798.vuongmp3.ui.playlist

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.base.BaseActivity
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.service.MediaPlayerService
import com.vuongnq14798.vuongmp3.ui.playmusic.PlayMusicActivity
import com.vuongnq14798.vuongmp3.util.TrackDiffCallBack
import kotlinx.android.synthetic.main.activity_detail_genre.*
import kotlinx.android.synthetic.main.activity_playlist.*

class PlaylistActivity : BaseActivity(),
    TrackAdapter.OnTrackClickListener {

    private lateinit var mediaPlayerService: MediaPlayerService
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.MediaPlayerBinder
            mediaPlayerService = binder.getService()
            mediaPlayerService.getTracks()?.let { bindData(it) }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }
    override val layoutResId: Int get() = R.layout.activity_playlist

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initComponents() {
        setSupportActionBar(toolbarPlaylist)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindData(tracks: List<Track>) {
        val trackAdapter = TrackAdapter(TrackDiffCallBack(), this)
        playlistRecycler.adapter = trackAdapter
        trackAdapter.submitList(tracks)
    }
    override fun onStart() {
        super.onStart()
        bindService(MediaPlayerService.getIntent(this), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onTrackClicked(track: Track) {
        mediaPlayerService.changeTrack(track)
        startActivity(PlayMusicActivity.getIntent(this).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, PlaylistActivity::class.java)
    }
}