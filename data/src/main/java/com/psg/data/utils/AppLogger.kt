package com.psg.data.utils

import android.util.Log

class AppLogger {
    companion object{
        fun d(tag:String,msg:String){
            if (Constants.DEBUG){
                Log.d(tag, msg)
            }
        }

        fun i(tag:String,msg:String){
            if (Constants.DEBUG){
                Log.i(tag, msg)
            }
        }

        fun p(msg:String){
            if (Constants.DEBUG){
                println(msg)
            }
        }

    }
}