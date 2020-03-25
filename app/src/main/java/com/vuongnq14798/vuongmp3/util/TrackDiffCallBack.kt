package com.vuongnq14798.vuongmp3.util

import androidx.recyclerview.widget.DiffUtil
import com.vuongnq14798.vuongmp3.data.model.Track

class TrackDiffCallBack : DiffUtil.ItemCallback<Track>() {

    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem.id.equals(newItem.id)

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem.equals(newItem)
}
