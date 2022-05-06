package com.psg.leagueoflegend_app.module

import androidx.room.Room
import com.psg.data.db.AppDatabase
import com.psg.data.repository.key.local.KeyLocalDataSource
import com.psg.data.repository.key.local.KeyLocalDataSourceImpl
import com.psg.data.repository.lol.local.LolLocalDataSource
import com.psg.data.repository.lol.local.LolLocalDataSourceImpl
import com.psg.leagueoflegend_app.di.LoLApp
import com.psg.data.utils.MiniSeriesTypeConverter
import com.psg.data.utils.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localDataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "lol-app.db")
            .addTypeConverter(MiniSeriesTypeConverter(LoLApp.getGson()))
            .build()
    }
    single { get<AppDatabase>().LoLDao() }

    single<LolLocalDataSource> { LolLocalDataSourceImpl(get()) }
    single<KeyLocalDataSource> { KeyLocalDataSourceImpl(get()) }
    single { PreferenceManager(get()) }
}
