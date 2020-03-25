package com.vuongnq14798.vuongmp3.ui.detailgenre

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Toast
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.base.BaseActivity
import com.vuongnq14798.vuongmp3.data.model.Genre
import com.vuongnq14798.vuongmp3.data.model.Track
import com.vuongnq14798.vuongmp3.data.source.TracksRepository
import com.vuongnq14798.vuongmp3.data.source.local.TracksLocalDataSource
import com.vuongnq14798.vuongmp3.data.source.remote.TracksRemoteDataSource
import com.vuongnq14798.vuongmp3.service.MediaPlayerService
import com.vuongnq14798.vuongmp3.ui.playmusic.PlayMusicActivity
import com.vuongnq14798.vuongmp3.util.TrackDiffCallBack
import com.vuongnq14798.vuongmp3.util.ContextExtensions.toast
import kotlinx.android.synthetic.main.activity_detail_genre.*
import java.lang.Exception
import java.util.*

class DetailGenreActivity : BaseActivity(), DetailGenreContract.View,
    DetailGenreAdapter.OnTrackClickListener {

    private lateinit var mediaPlayerService: MediaPlayerService
    private lateinit var genre: Genre
    private val tracks = mutableListOf<Track>()

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.MediaPlayerBinder
            mediaPlayerService = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    private val tracksRepository: TracksRepository by lazy {
        TracksRepository(
            TracksRemoteDataSource.getInstance(),
            TracksLocalDataSource.getInstance(this)
        )
    }
    private val detailGenrePresenter: DetailGenrePresenter by lazy {
        DetailGenrePresenter(
            tracksRepository,
            this
        )
    }
    override val layoutResId: Int = R.layout.activity_detail_genre

    override fun initData(savedInstanceState: Bundle?) {
        genre = intent.getParcelableExtra(GENRE_INFO)
    }

    override fun initComponents() {

        detailGenrePresenter.getTracksRemote(genre.genreURL)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar.title = genre.genreName.toUpperCase(Locale.getDefault())
        genreImage.setImageResource(genre.genreImage)

        fab.setOnClickListener {
            mediaPlayerService.let {
                if (tracks.isNotEmpty()) {
                    it.updateTracks(tracks)
                    it.changeTrack(tracks[0])
                    startActivity(PlayMusicActivity.getIntent(this))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(MediaPlayerService.getIntent(this), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
    override fun showTracks(tracks: List<Track>) {
        this.tracks.addAll(tracks)
        val trackAdapter = DetailGenreAdapter(TrackDiffCallBack(), this)
        detailGenre.adapter = trackAdapter
        trackAdapter.submitList(tracks)
    }

    override fun showError(exception: Exception) {
        toast(getString(R.string.error_message))
    }

    override fun onTrackClicked(track: Track) {
        mediaPlayerService.changeTrack(track)
        startActivity(PlayMusicActivity.getIntent(this))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val GENRE_INFO = "genre_info"
        fun getIntent(context: Context, genre: Genre): Intent =
            Intent(context, DetailGenreActivity::class.java).putExtra(GENRE_INFO, genre)
    }
}
