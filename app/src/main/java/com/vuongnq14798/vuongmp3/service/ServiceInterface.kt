package com.vuongnq14798.vuongmp3.service

import com.vuongnq14798.vuongmp3.data.model.Track

interface ServiceInterface {

    fun changeTrack(track: Track)

    fun startTrack()

    fun pauseTrack()

    fun isPlaying(): Boolean

    fun getDuration(): Int

    fun seekTo(position: Int)

    fun getCurrentTrack(): Track?

    fun setCurrentTrack(track: Track?)

    fun getCurrentPosition(): Int

    fun nextTrack()

    fun setServiceListener(listener: ServiceListener)

    fun previousTrack()

    fun addTrack(track: Track)

    fun updateTracks(newTracks: List<Track>)

    fun getTracks(): List<Track>

    fun getShuffleType(): Int

    fun setShuffleType(type: Int)

    fun getLoopType(): Int

    fun setLoopType(type: Int)

    fun removeTrack(track: Track)

    fun clearTracks()

    fun onFailure()
}
