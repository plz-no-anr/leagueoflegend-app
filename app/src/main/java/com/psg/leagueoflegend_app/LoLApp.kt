package com.psg.leagueoflegend_app

import android.app.Application
import android.content.Context

class LoLApp: Application() {

    companion object{
        lateinit var INSTANCE: LoLApp
        fun getContext(): Context = INSTANCE.applicationContext
    }

    init {
        INSTANCE = this
    }

}