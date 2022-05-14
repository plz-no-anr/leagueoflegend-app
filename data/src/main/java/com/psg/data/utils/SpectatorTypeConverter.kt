package com.psg.data.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.psg.data.model.remote.SpectatorResponse

@ProvidedTypeConverter
class SpectatorTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: SpectatorResponse): String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): SpectatorResponse {
        return gson.fromJson(value, SpectatorResponse::class.java)
    }

}