package com.psg.leagueoflegend_app.utils

import android.util.Log
import org.koin.android.BuildConfig

class AppLogger {
    companion object{
        private const val isDebug = BuildConfig.DEBUG

        fun d(tag:String,msg:String){
            if (isDebug){
                Log.d(tag, msg)
            }
        }

        fun i(tag:String,msg:String){
            if (isDebug){
                Log.i(tag, msg)
            }
        }

        fun p(print:String){
            if (isDebug){
                println(print)
            }
        }

    }
}