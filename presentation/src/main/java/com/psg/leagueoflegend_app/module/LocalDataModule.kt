package com.psg.leagueoflegend_app.module

import android.content.Context
import androidx.room.Room
import com.psg.data.db.AppDatabase
import com.psg.data.db.LoLDao
import com.psg.data.repository.key.local.KeyLocalDataSource
import com.psg.data.repository.key.local.KeyLocalDataSourceImpl
import com.psg.data.repository.lol.local.LolLocalDataSource
import com.psg.data.repository.lol.local.LolLocalDataSourceImpl
import com.psg.data.utils.MiniSeriesTypeConverter
import com.psg.data.utils.PreferenceManager
import com.psg.leagueoflegend_app.di.LoLApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "lol-app.db")
            .addTypeConverter(MiniSeriesTypeConverter(LoLApp.getGson()))
            .build()

    @Provides
    @Singleton
    fun provideLolDao(database: AppDatabase): LoLDao = database.LoLDao()

    @Provides
    @Singleton
    fun provideLolLocalData(dao: LoLDao): LolLocalDataSource = LolLocalDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideKeyLocalData(preferenceManager: PreferenceManager): KeyLocalDataSource = KeyLocalDataSourceImpl(preferenceManager)

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager = PreferenceManager(context)

}
