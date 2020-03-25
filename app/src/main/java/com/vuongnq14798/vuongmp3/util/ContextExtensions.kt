package com.vuongnq14798.vuongmp3.util

import android.content.Context
import android.widget.Toast

object ContextExtensions {
    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
