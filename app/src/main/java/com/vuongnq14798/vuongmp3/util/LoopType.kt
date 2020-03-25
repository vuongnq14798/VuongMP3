package com.vuongnq14798.vuongmp3.util

import androidx.annotation.IntDef

@IntDef(
    LoopType.NONE,
    LoopType.ONE,
    LoopType.ALL
)
annotation class LoopType {
    companion object {
        const val NONE = 0
        const val ONE = 1
        const val ALL = 2
    }
}
