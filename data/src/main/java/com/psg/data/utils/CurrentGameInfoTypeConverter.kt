package com.psg.data.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.psg.data.model.remote.CurrentGameInfo

@ProvidedTypeConverter
class CurrentGameInfoTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: CurrentGameInfo): String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): CurrentGameInfo {
        return gson.fromJson(value, CurrentGameInfo::class.java)
    }

}