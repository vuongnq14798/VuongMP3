package com.vuongnq14798.vuongmp3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.util.ImageUtils
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter(
    private val diffCallBack: DiffUtil.ItemCallback<Genre>,
    private val genreClickListener: OnGenreClickListener
) : ListAdapter<Genre, GenreAdapter.ViewHolder>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_genre, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(getItem(position), genreClickListener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(genre: Genre, genreClickListener: OnGenreClickListener) {
            itemView.apply {
                textGenre.text = genre.genreName
                ImageUtils.loadImage(genreImage, genre.genreImage)
            }

            itemView.setOnClickListener { genreClickListener.onGenreClicked(genre) }
        }
    }

    interface OnGenreClickListener {
        fun onGenreClicked(genre: Genre)
    }
}
