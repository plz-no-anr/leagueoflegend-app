package com.psg.leagueoflegend_app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.psg.leagueoflegend_app.data.di.appModule
import com.psg.leagueoflegend_app.data.di.repositoryModule
import com.psg.leagueoflegend_app.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LoLApp: Application() {

    companion object{
        lateinit var INSTANCE: LoLApp
        lateinit var pref: AppPrefUtil
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
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
        pref = AppPrefUtil(applicationContext)
    }


    class AppPrefUtil(context: Context) {
        private val prefs: SharedPreferences = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

        fun getApikey() = prefs.getString("apikey","")

        fun setApikey(value: String) {
            prefs.edit().putString("apikey", value).apply()
        }
        fun delApikey(){
            prefs.edit().remove("apikey").apply()
        }

    }


}