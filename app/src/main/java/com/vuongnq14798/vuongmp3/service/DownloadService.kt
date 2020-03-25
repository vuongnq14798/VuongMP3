package com.vuongnq14798.vuongmp3.service

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.util.Constants
import com.vuongnq14798.vuongmp3.util.StringUtils
import java.io.File


class DownloadService : IntentService("Download Track") {

    override fun onHandleIntent(intent: Intent?) {
        val track = intent?.getParcelableExtra<Track>(TRACK_INFO)

        val url = track?.streamUrl
        val fileName = StringUtils.initFileName(track?.title)
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle(track?.title)
            setDescription(getString(R.string.description))
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Constants.Track.DIR_TYPE, fileName)
        }

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }


    companion object {
        private const val TRACK_INFO = "track_info"
        fun getIntent(context: Context, track: Track): Intent =
            Intent(context, DownloadService::class.java).putExtra(TRACK_INFO, track)
    }

}