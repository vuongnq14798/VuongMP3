package com.vuongnq14798.vuongmp3.util

import androidx.annotation.IntDef

@IntDef(
    ShuffleType.ON,
    ShuffleType.OFF
)
annotation class ShuffleType {
    companion object {
        const val ON = 1
        const val OFF = 0
    }
}
