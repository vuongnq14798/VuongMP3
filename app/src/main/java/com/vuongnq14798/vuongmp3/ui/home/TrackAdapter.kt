package com.vuongnq14798.vuongmp3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.util.ImageUtils
import kotlinx.android.synthetic.main.item_track.view.*

class TrackAdapter(
    private val diffCallBack: DiffUtil.ItemCallback<Track>,
    private val trackClickListener: OnTrackClickListener
) : ListAdapter<Track, TrackAdapter.ViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_track, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(getItem(position), trackClickListener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(track: Track, TrackClickListener: OnTrackClickListener) {
            itemView.apply {
                textTrackName.text = track.title
                textArtistName.text = track.artist
                ImageUtils.loadImage(trackImage, track.artworkUrl)
            }

            itemView.setOnClickListener { TrackClickListener.onTrackClicked(track) }
        }
    }

    interface OnTrackClickListener {
        fun onTrackClicked(track: Track)
    }
}
