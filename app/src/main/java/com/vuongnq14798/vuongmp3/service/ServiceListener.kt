package com.vuongnq14798.vuongmp3.service

import com.vuongnq14798.vuongmp3.util.StateType

interface ServiceListener {
    fun onChangeTrackListener()
    fun onPlayingStateListener(@StateType state: Int)
}
