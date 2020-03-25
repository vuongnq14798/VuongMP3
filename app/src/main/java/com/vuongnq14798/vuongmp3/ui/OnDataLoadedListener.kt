package com.vuongnq14798.vuongmp3.ui

interface OnDataLoadedListener<T> {

    fun onSuccess(data: T)
    fun onFailed(exception: Exception)
}
