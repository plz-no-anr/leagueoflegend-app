package com.psg.leagueoflegend_app.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.psg.leagueoflegend_app.data.model.SummonerEntity

@ProvidedTypeConverter
class MiniSeriesTypeConverter(private val gson: Gson) {

    @TypeConverter
    fun listToJson(value: SummonerEntity.MiniSeries): String{
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): SummonerEntity.MiniSeries{
        return gson.fromJson(value, SummonerEntity.MiniSeries::class.java)
    }

}