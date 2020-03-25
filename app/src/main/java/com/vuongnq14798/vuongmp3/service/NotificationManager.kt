package com.vuongnq14798.vuongmp3.service

import android.app.*
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.bumptech.glide.request.target.NotificationTarget
import com.vuongnq14798.vuongmp3.BuildConfig
import com.vuongnq14798.vuongmp3.MainActivity
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.ui.playmusic.PlayMusicActivity
import com.vuongnq14798.vuongmp3.util.ImageUtils

class NotificationManager(private val context: Context)  {

    private lateinit var notificationManager: NotificationManager
    private val remoteSmallViews = RemoteViews(PACKAGE_NAME, R.layout.notification_small)
    private val remoteBigViews = RemoteViews(PACKAGE_NAME, R.layout.notification_big)

    fun updateNotification(track: Track, isPlaying: Boolean) : Notification{

        val stackBuilder = TaskStackBuilder.create(context).apply {
            addNextIntentWithParentStack(PlayMusicActivity.getIntent(context))
            addParentStack(MainActivity::class.java)
        }

        val pendingIntent = stackBuilder.getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_play)
            .setContentText(context.getString(R.string.app_name))
            .setCustomContentView(remoteSmallViews)
            .setCustomBigContentView(remoteBigViews)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notification = builder.build()

        initData(remoteSmallViews, track, notification, isPlaying)
        initData(remoteBigViews, track, notification, isPlaying)

        setOnClick(MediaPlayerService.ACTION_NEXT, R.id.imageNext)
        setOnClick(MediaPlayerService.ACTION_PLAY, R.id.imagePlay)
        setOnClick(MediaPlayerService.ACTION_PREVIOUS, R.id.imagePrevious)
        setOnClick(MediaPlayerService.ACTION_CLOSE, R.id.imageClose)

        return notification
    }

    fun updateIcon(isPlaying: Boolean) {
        if (isPlaying) {
            Log.d("Vuong", "pause")
            remoteBigViews.setImageViewResource(R.id.imagePlay, R.drawable.ic_pause)
            remoteSmallViews.setImageViewResource(R.id.imagePlay, R.drawable.ic_pause)
        } else {
            Log.d("Vuong", "play")
            remoteBigViews.setImageViewResource(R.id.imagePlay, R.drawable.ic_play)
            remoteSmallViews.setImageViewResource(R.id.imagePlay, R.drawable.ic_play)
        }
    }

    private fun setOnClick(action: String, resId: Int) {

        val intent = MediaPlayerService.getIntent(context)
        intent.action = action
        val pendingIntent = PendingIntent.getService(context, REQUEST_CODE, intent, 0)
        remoteSmallViews.setOnClickPendingIntent(resId, pendingIntent)
        remoteBigViews.setOnClickPendingIntent(resId, pendingIntent)
    }

    private fun initData(
        remoteViews: RemoteViews,
        track: Track,
        notification: Notification,
        isPlaying: Boolean
    ) {
        remoteViews.apply {
            setTextViewText(R.id.textSongName, track.title)
            setTextViewText(R.id.textArtist, track.artist)
            setImageViewResource(
                R.id.imagePlay,
                if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
            )
        }
        val target = NotificationTarget(context, R.id.artwork, remoteViews, notification, NOTIFICATION_ID)
        ImageUtils.loadImageNotification(context, target, track.artworkUrl)
    }

    fun createNotification() {

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val PACKAGE_NAME = BuildConfig.APPLICATION_ID
        private const val CHANNEL_NAME = "${PACKAGE_NAME}.ChannelName"
        private const val CHANNEL_ID = "${PACKAGE_NAME}.ChannelID"
        private const val REQUEST_CODE = 1
        const val NOTIFICATION_ID = 2
    }
}
