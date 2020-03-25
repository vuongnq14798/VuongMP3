package com.vuongnq14798.vuongmp3.mediaplayer

import com.vuongnq14798.vuongmp3.data.model.Track

interface MediaPlayerInterface {

    val tracks: MutableList<Track>

    var currentTrack: Track?

    val currentPosition: Int

    val isPlaying: Boolean

    val duration: Int

    fun create(track: Track)

    fun reset()

    fun start()

    fun pause()

    fun stop()

    fun release()

    fun seekTo(position: Int)

    fun nextTrack()

    fun previousTrack()

    fun changeTrack(track: Track)

    fun isEndOfList(): Boolean

    fun addTrack(track: Track)

    fun updateTracks(newTracks: List<Track>)

    fun removeTrack(track: Track)

    fun clearTracks()

}
