package com.psg.leagueoflegend_app

import android.app.Application
import android.content.Context
import com.psg.leagueoflegend_app.data.di.apiModule
import com.psg.leagueoflegend_app.data.di.repositoryModule
import com.psg.leagueoflegend_app.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LoLApp: Application() {

    companion object{
        lateinit var INSTANCE: LoLApp
        fun getContext(): Context = INSTANCE.applicationContext
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@LoLApp)
            modules(listOf(apiModule, viewModelModule, repositoryModule))
        }
    }


}