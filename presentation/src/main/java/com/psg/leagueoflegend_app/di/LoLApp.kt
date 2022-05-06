package com.psg.leagueoflegend_app.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.psg.leagueoflegend_app.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LoLApp: Application() {

    companion object{
        lateinit var INSTANCE: LoLApp
        fun getContext(): Context = INSTANCE.applicationContext
        fun getGson(): Gson = Gson()
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@LoLApp)
            modules(
                apiModule,
                localDataModule,
                remoteDataModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }






}