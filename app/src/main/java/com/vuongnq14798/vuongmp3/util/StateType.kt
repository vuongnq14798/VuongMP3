package com.vuongnq14798.vuongmp3.util

import androidx.annotation.IntDef

@IntDef(
    StateType.IDLE,
    StateType.PREPARED,
    StateType.PLAYING,
    StateType.PAUSED,
    StateType.STOPPED
)
annotation class StateType {
    companion object {
        const val IDLE = 0
        const val PREPARED = 1
        const val PLAYING = 2
        const val PAUSED = 3
        const val STOPPED = 4
    }
}
