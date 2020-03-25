package com.vuongnq14798.vuongmp3.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.vuongnq14798.vuongmp3.BuildConfig
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.mediaplayer.MediaPlayerManager
import com.vuongnq14798.vuongmp3.util.LoopType
import com.vuongnq14798.vuongmp3.util.StateType
import java.lang.IllegalStateException

class MediaPlayerService : Service(),
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnCompletionListener,
    ServiceInterface {

    private lateinit var binder: IBinder
    private var listener: ServiceListener? = null

    private val mediaPlayerManager: MediaPlayerManager by lazy { MediaPlayerManager.getInstance(this) }
    private val notificationManager: NotificationManager by lazy { NotificationManager(this) }
    override fun onCreate() {
        super.onCreate()
        binder = MediaPlayerBinder()
    }

    override fun onBind(intent: Intent?): IBinder? = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (intent.action) {
                ACTION_PLAY -> {
                    if(isPlaying()) {
                        mediaPlayerManager.pause()
                    } else {
                        mediaPlayerManager.start()
                    }
                    getCurrentTrack()?.let { notificationManager.updateNotification(it, isPlaying()) }
                }
                ACTION_NEXT -> {
                    mediaPlayerManager.nextTrack()
                    getCurrentTrack()?.let { notificationManager.updateNotification(it, isPlaying()) }
                }
                ACTION_PREVIOUS -> {
                    mediaPlayerManager.previousTrack()
                    getCurrentTrack()?.let { notificationManager.updateNotification(it, isPlaying()) }
                }
                ACTION_CLOSE -> {
                    stopForeground(true)
                    stopSelf()
                }
                else -> {}
            }
        }
        return START_NOT_STICKY
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mediaPlayerManager.start()
        getCurrentTrack()?.let {
            startService(getIntent(this))
            notificationManager.createNotification()
            startForeground(
                NotificationManager.NOTIFICATION_ID,
                notificationManager.updateNotification(it, isPlaying())
            )
        }
        listener?.apply {
            try{
                onChangeTrackListener()
                onPlayingStateListener(StateType.PLAYING)
            } catch (e: IllegalStateException){
                //todo
            }
        }
    }

    override fun onError(mediaPlayer: MediaPlayer?, what: Int, extra: Int): Boolean = run {
        onFailure()
        true
    }

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        when (mediaPlayerManager.loopType) {
            LoopType.NONE -> {
                if (mediaPlayerManager.isEndOfList()) mediaPlayerManager.stop()
                else nextTrack()
            }
            LoopType.ALL -> nextTrack()

            LoopType.ONE -> mediaPlayerManager.start()
        }
    }

    override fun onDestroy() {
        mediaPlayerManager.stop()
        mediaPlayerManager.reset()
        super.onDestroy()
    }

    override fun changeTrack(track: Track) = mediaPlayerManager.changeTrack(track)

    override fun pauseTrack() {
        mediaPlayerManager.pause()
        listener?.onPlayingStateListener(StateType.PAUSED)
        getCurrentTrack()?.let { notificationManager.updateNotification(it, isPlaying()) }

    }

    override fun startTrack() {
        mediaPlayerManager.start()
        getCurrentTrack()?.let {
            startService(getIntent(this))
            notificationManager.createNotification()
            startForeground(
                NotificationManager.NOTIFICATION_ID,
                notificationManager.updateNotification(it, isPlaying())
            )
        }
        listener?.apply {
            onChangeTrackListener()
            onPlayingStateListener(StateType.PLAYING)
        }
    }

    override fun previousTrack() {
        mediaPlayerManager.previousTrack()
        listener?.onChangeTrackListener()
        getCurrentTrack()?.let { notificationManager.updateNotification(it, isPlaying()) }

    }

    override fun nextTrack() {
        mediaPlayerManager.nextTrack()
        listener?.onChangeTrackListener()
        getCurrentTrack()?.let { notificationManager.updateNotification(it, isPlaying()) }
    }

    override fun isPlaying(): Boolean = mediaPlayerManager.isPlaying

    override fun getDuration(): Int = mediaPlayerManager.duration

    override fun seekTo(position: Int) = mediaPlayerManager.seekTo(position)

    override fun getCurrentTrack(): Track? = mediaPlayerManager.currentTrack

    override fun setCurrentTrack(track: Track?) {
        mediaPlayerManager.currentTrack = track
    }

    override fun getCurrentPosition()= try{
        mediaPlayerManager.currentPosition
    } catch (e: IllegalStateException) {
        0
    }

    override fun setServiceListener(listener: ServiceListener) {
        this.listener = listener
    }

    override fun addTrack(track: Track) = mediaPlayerManager.addTrack(track)

    override fun updateTracks(newTracks: List<Track>) = mediaPlayerManager.updateTracks(newTracks)

    override fun getTracks(): List<Track> = mediaPlayerManager.tracks

    override fun getShuffleType(): Int = mediaPlayerManager.shuffleType

    override fun setShuffleType(type: Int) {
        mediaPlayerManager.shuffleType = type
    }

    override fun getLoopType(): Int = mediaPlayerManager.loopType

    override fun setLoopType(type: Int) {
        mediaPlayerManager.loopType = type
    }

    override fun removeTrack(track: Track) = mediaPlayerManager.removeTrack(track)

    override fun clearTracks() = mediaPlayerManager.clearTracks()

    override fun onFailure() = this.toast(getString(R.string.service_on_failure))

    private fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    inner class MediaPlayerBinder : Binder() {
        fun getService(): MediaPlayerService = this@MediaPlayerService
    }

    companion object {

        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        val ACTION_PLAY = "$PACKAGE_NAME.play"
        val ACTION_PREVIOUS = "$PACKAGE_NAME.previous"
        val ACTION_NEXT = "$PACKAGE_NAME.next"
        val ACTION_CLOSE = "$PACKAGE_NAME.close"

        private var INSTANCE: MediaPlayerService? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MediaPlayerService().also { INSTANCE = it }
        }

        fun getIntent(context: Context) = Intent(context, MediaPlayerService::class.java)
    }
}
