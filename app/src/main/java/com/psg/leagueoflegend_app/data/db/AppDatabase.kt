package com.psg.leagueoflegend_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.psg.leagueoflegend_app.data.db.dao.LoLDao
import com.psg.leagueoflegend_app.data.model.ProfileEntity
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.utils.CurrentGameInfoTypeConverter
import com.psg.leagueoflegend_app.utils.MiniSeriesTypeConverter

@Database(entities = [SummonerEntity::class, SearchEntity::class, ProfileEntity::class], version = 1)
@TypeConverters(
    value = [
        MiniSeriesTypeConverter::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun LoLDao(): LoLDao
}