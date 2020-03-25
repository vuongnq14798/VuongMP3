package com.vuongnq14798.vuongmp3.mediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.service.MediaPlayerService
import com.vuongnq14798.vuongmp3.util.LoopType
import com.vuongnq14798.vuongmp3.util.ShuffleType
import com.vuongnq14798.vuongmp3.util.StateType
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.random.Random

class MediaPlayerManager(
    private val mediaPlayerService: MediaPlayerService
) : MediaPlayerInterface {

    @StateType var state = StateType.PAUSED
    @ShuffleType var shuffleType = ShuffleType.OFF
    @LoopType var loopType = LoopType.NONE
    private val mediaPlayer = MediaPlayer()
    private var position = 0

    override val tracks = mutableListOf<Track>()

    override var currentTrack: Track? = null

    override val currentPosition: Int get() = mediaPlayer.currentPosition

    override val isPlaying: Boolean get() = mediaPlayer.isPlaying

    override val duration: Int get() = mediaPlayer.duration

    override fun create(track: Track) {

        mediaPlayer.reset()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )

        try {
            if (track.isOnline) {
                mediaPlayer.setDataSource(mediaPlayerService, Uri.parse(track.streamUrl))
            } else {
                mediaPlayer.setDataSource(track.streamUrl)
            }
        } catch (e: IOException) {
            //todo handle Exception
        } catch (e: IllegalStateException) {
            //todo handle Exception
        } catch (e: IllegalArgumentException) {
            //todo handle Exception
        } catch (e: SecurityException) {
            //todo handle Exception
        }

        mediaPlayer.apply {
            setOnErrorListener(mediaPlayerService)
            setOnCompletionListener(mediaPlayerService)
            setOnPreparedListener(mediaPlayerService)

        }

        try {
            mediaPlayer.prepareAsync()
        } catch (e: IllegalStateException) {
            //todo handle Exception
        }
    }

    override fun reset() {
        mediaPlayer.reset()
    }

    override fun start() {
        state = StateType.PLAYING
        mediaPlayer.start()
    }

    override fun pause() {
        state = StateType.PAUSED
        mediaPlayer.pause()
    }

    override fun stop() {
        state = StateType.PLAYING
        mediaPlayer.stop()
    }

    override fun release() = mediaPlayer.release()

    override fun seekTo(position: Int) = mediaPlayer.seekTo(position)

    override fun nextTrack() {
        if (shuffleType == ShuffleType.OFF) {
            changeTrack(getNextTrack())
        } else {
            changeTrack(tracks[Random.nextInt(tracks.size)])
        }
    }

    override fun previousTrack() {
        if (shuffleType == ShuffleType.OFF) {
            changeTrack(getPreviousTrack())
        } else {
            changeTrack(tracks[Random.nextInt(tracks.size)])
        }
    }

    override fun changeTrack(track: Track) {
        if (tracks.indexOf(track) < 0) addTrack(track)
        position = tracks.indexOf(track)
        currentTrack = track
        create(track)
    }

    override fun isEndOfList(): Boolean =
        currentTrack == null || position + 1 == tracks.size

    override fun addTrack(track: Track) {
        tracks.add(track)
    }

    override fun updateTracks(newTracks: List<Track>) {
        tracks.apply {
            position = 0
            clear()
            tracks.addAll(newTracks)
        }
    }

    override fun removeTrack(track: Track) {
        position = 0
        tracks.remove(track)
    }

    override fun clearTracks() {
        position = 0
        tracks.clear()
    }

    private fun getNextTrack(): Track = tracks[(position + 1) % tracks.size]

    private fun getPreviousTrack(): Track = tracks[(position - 1 + tracks.size) % tracks.size]

    companion object {

        private var INSTANCE: MediaPlayerManager? = null

        fun getInstance(mediaPlayerService: MediaPlayerService) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MediaPlayerManager(mediaPlayerService).also { INSTANCE = it }
        }
    }
}
