package com.vuongnq14798.vuongmp3

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.IBinder
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vuongnq14798.vuongmp3.base.BaseActivity
import com.vuongnq14798.vuongmp3.service.MediaPlayerService
import com.vuongnq14798.vuongmp3.ui.PermissionsManager
import com.vuongnq14798.vuongmp3.ui.downloaded.DownloadedFragment
import com.vuongnq14798.vuongmp3.ui.home.HomeFragment
import com.vuongnq14798.vuongmp3.ui.miniplay.MiniPlayFragment
import com.vuongnq14798.vuongmp3.ui.mymusic.MyMusicFragment
import com.vuongnq14798.vuongmp3.ui.search.SearchFragment
import com.vuongnq14798.vuongmp3.util.ContextExtensions.toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var permissionsManager: PermissionsManager
    private lateinit var mediaPlayerService: MediaPlayerService
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MediaPlayerService.MediaPlayerBinder
            mediaPlayerService = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    override val layoutResId: Int = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        permissionsManager = PermissionsManager(
            this,
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
        permissionsManager.checkPermissions()

        if(isNetworkAvailable(this)) {
            addFragment(R.id.frameContainer ,HomeFragment())
        }
    }

    override fun initComponents() {
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        bindService(MediaPlayerService.getIntent(this), connection, Context.BIND_AUTO_CREATE)
        if (::mediaPlayerService.isInitialized && mediaPlayerService.getTracks().isNotEmpty())
            replaceFragment(R.id.frameMiniPlay, MiniPlayFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        connection?.let { unbindService(it) }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (element in grantResults) {
                if (element == PackageManager.PERMISSION_DENIED) {
                    finish()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val fragment = when (item.itemId) {
            R.id.home -> if(isNetworkAvailable(this)) {
                HomeFragment()
            } else {
                return false
            }
            R.id.my_music -> MyMusicFragment()
            R.id.downloaded_songs -> DownloadedFragment()
            R.id.search -> if(isNetworkAvailable(this)){
                SearchFragment()
            } else {
                return false
            }
            else -> return false
        }
        replaceFragment(R.id.frameContainer, fragment)
        return true
    }

    private fun addFragment(containerViewId: Int,fragment: Fragment) =
        supportFragmentManager
            .beginTransaction()
            .replace(
                containerViewId,
                fragment,
                fragment.javaClass.simpleName
            )
            .commit()

    private fun replaceFragment(containerViewId: Int,fragment: Fragment) =
        supportFragmentManager
            .beginTransaction()
            .add(
                containerViewId,
                fragment,
                fragment.javaClass.simpleName
            )
            .commit()

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
    fun getMediaPlayerService(): MediaPlayerService? =
        if (::mediaPlayerService.isInitialized) mediaPlayerService else null

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
