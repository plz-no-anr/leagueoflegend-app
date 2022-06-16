package com.psg.leagueoflegend_app.module

import com.psg.data.repository.key.KeyRepositoryImpl
import com.psg.data.repository.key.local.KeyLocalDataSource
import com.psg.data.repository.lol.LolRepositoryImpl
import com.psg.data.repository.lol.local.LolLocalDataSource
import com.psg.data.repository.lol.remote.LolRemoteDataSource
import com.psg.data.utils.JsonUtils
import com.psg.domain.repository.KeyRepository
import com.psg.domain.repository.LolRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{
    @Provides
    @Singleton
    fun provideLolRepository(
        lolRemoteDataSource: LolRemoteDataSource,
        lolLocalDataSource: LolLocalDataSource,
        keyLocalDataSource: KeyLocalDataSource,
        jsonUtils: JsonUtils
    ): LolRepository = LolRepositoryImpl(lolRemoteDataSource, lolLocalDataSource,keyLocalDataSource, jsonUtils)

    @Provides
    @Singleton
    fun provideKeyRepository(keyLocalDataSource: KeyLocalDataSource): KeyRepository = KeyRepositoryImpl(keyLocalDataSource)
}