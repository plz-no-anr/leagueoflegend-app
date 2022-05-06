package com.psg.data.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(LOL_APP, Context.MODE_PRIVATE)

    var apiKey: String?
    get() = prefs.getString(API_KEY, "")
    set(value) {
        prefs.edit().putString(API_KEY, value).apply()
    }

//    fun getApikey() = prefs.getString(API_KEY, "")
//
//    fun setApikey(value: String) {
//        prefs.edit().putString("apikey", value).apply()
//    }

//    fun delApikey() {
//        prefs.edit().remove("apikey").apply()
//    }

    companion object {
        private const val LOL_APP = "LOL_APP"
        const val API_KEY = "API_KEY"
    }

}