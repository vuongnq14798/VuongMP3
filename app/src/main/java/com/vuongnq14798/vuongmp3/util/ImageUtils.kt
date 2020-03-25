package com.vuongnq14798.vuongmp3.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Notification
import android.content.Context
import android.widget.ImageView
import androidx.core.os.trace
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.request.target.NotificationTarget
import com.vuongnq14798.vuongmp3.R

object ImageUtils {

    fun loadImage(image: ImageView, link: Any?) {
        Glide
            .with(image.context)
            .load(link)
            .error(R.drawable.ic_music)
            .into(image)
    }

    fun loadCircleImage(image: ImageView, link: String) {
        Glide.with(image.context)
            .load(link)
            .fallback(R.drawable.ic_music)
            .error(R.drawable.ic_music)
            .circleCrop()
            .into(image)
    }

    fun rotateImage(image: ImageView) {
        image.animate()
            .setDuration(10)
            .rotation(image.rotation + 5f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    rotateImage(image)
                    super.onAnimationEnd(animation)
                }
            })
    }

    fun loadImageNotification(context: Context, target: NotificationTarget, link: String?) {
        Glide.with(context)
            .asBitmap()
            .load(link)
            .fallback(R.drawable.ic_music)
            .error(R.drawable.ic_music)
            .into(target)
    }
}
