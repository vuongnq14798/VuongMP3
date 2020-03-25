package com.vuongnq14798.vuongmp3.ui.miniplay

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.base.BaseFragment
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.service.MediaPlayerService
import com.vuongnq14798.vuongmp3.service.ServiceListener
import com.vuongnq14798.vuongmp3.ui.playmusic.PlayMusicActivity
import com.vuongnq14798.vuongmp3.util.ImageUtils
import com.vuongnq14798.vuongmp3.util.StateType
import kotlinx.android.synthetic.main.fragment_mini_play.artwork
import kotlinx.android.synthetic.main.fragment_mini_play.next
import kotlinx.android.synthetic.main.fragment_mini_play.play
import kotlinx.android.synthetic.main.fragment_mini_play.previous
import kotlinx.android.synthetic.main.fragment_mini_play.textArtistName
import kotlinx.android.synthetic.main.fragment_mini_play.textTrackName
import java.lang.NullPointerException

class MiniPlayFragment : BaseFragment(),
    ServiceListener,
    View.OnClickListener {

    private lateinit var mediaPlayerService: MediaPlayerService
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.MediaPlayerBinder
            mediaPlayerService = binder.getService()
            mediaPlayerService.setServiceListener(this@MiniPlayFragment)
            mediaPlayerService.getCurrentTrack()?.let { bindData(it) }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    override val layoutResId: Int = R.layout.fragment_mini_play

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initComponents() {
        next.setOnClickListener(this)
        play.setOnClickListener(this)
        previous.setOnClickListener(this)
        artwork.setOnClickListener(this)
    }

    fun bindData(track: Track) {
        textArtistName.text = track.artist
        textTrackName.text = track.title
        track.artworkUrl?.let { ImageUtils.loadCircleImage(artwork, it) }
        updateStateIcon()
    }

    override fun onStart() {
        super.onStart()
        requireActivity().bindService(
            context?.let { MediaPlayerService.getIntent(it) },
            connection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unbindService(connection)
    }

    override fun onChangeTrackListener() {
        mediaPlayerService.getCurrentTrack()?.let { bindData(it) }
    }

    override fun onPlayingStateListener(state: Int) {
        if (state == StateType.PAUSED) {
            play.setImageResource(R.drawable.ic_play)
        } else {
            play.setImageResource(R.drawable.ic_pause)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.previous -> mediaPlayerService.previousTrack()
            R.id.play -> onClickPlayPause()
            R.id.next -> mediaPlayerService.nextTrack()
            else -> startActivity(context?.let { PlayMusicActivity.getIntent(it) })
        }
    }

    private fun updateStateIcon() {
        if (mediaPlayerService.isPlaying()) {
            play.setImageResource(R.drawable.ic_pause)
        } else {
            play.setImageResource(R.drawable.ic_play)
        }
    }

    private fun onClickPlayPause() {
        if (mediaPlayerService.isPlaying()) {
            mediaPlayerService.pauseTrack()
        } else {
            mediaPlayerService.startTrack()
        }
    }

    companion object {
        private var INSTANCE: MiniPlayFragment? = null
        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MiniPlayFragment().also { INSTANCE = it }
        }
    }
}
