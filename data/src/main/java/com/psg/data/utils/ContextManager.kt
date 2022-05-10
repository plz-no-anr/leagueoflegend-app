package com.psg.data.utils

import android.app.Application

object ContextManager:Application() {
    private val context = applicationContext

    fun getContext() = context
}