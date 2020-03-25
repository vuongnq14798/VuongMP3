package com.vuongnq14798.vuongmp3.util

import androidx.recyclerview.widget.DiffUtil
import com.vuongnq14798.vuongmp3.data.model.Genre

class GenreDiffCallBack : DiffUtil.ItemCallback<Genre>() {

    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
        oldItem.genreName.equals(newItem.genreName)

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
        oldItem.equals(newItem)
}
