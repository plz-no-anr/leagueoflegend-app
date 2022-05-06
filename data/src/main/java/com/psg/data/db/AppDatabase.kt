package com.psg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.psg.data.model.local.ProfileEntity
import com.psg.data.model.local.SearchEntity
import com.psg.data.model.local.SummonerEntity
import com.psg.data.utils.MiniSeriesTypeConverter

@Database(entities = [SummonerEntity::class, SearchEntity::class, ProfileEntity::class], version = 1)
@TypeConverters(
    value = [
        MiniSeriesTypeConverter::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun LoLDao(): LoLDao
}