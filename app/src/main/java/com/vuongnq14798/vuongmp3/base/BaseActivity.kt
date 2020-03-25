package com.vuongnq14798.vuongmp3.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        initData(savedInstanceState)
        initComponents()
    }

    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun initComponents()
}
