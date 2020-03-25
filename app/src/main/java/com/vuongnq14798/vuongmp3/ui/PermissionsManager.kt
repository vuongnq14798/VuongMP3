package com.vuongnq14798.vuongmp3.ui

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vuongnq14798.vuongmp3.R
import com.vuongnq14798.vuongmp3.util.ContextExtensions.toast

class PermissionsManager(
    private val activity: Activity,
    private val permissions: List<String>,
    private val code: Int
) {
    private  val listPermissions = mutableListOf<String>()

    fun checkPermissions (): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissions.add(permission)
            }
        }

        if (listPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissions.toTypedArray(), code)
            return false
        }

        return true
    }
}
