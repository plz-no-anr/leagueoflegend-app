package com.psg.leagueoflegend_app.module

import com.psg.domain.repository.KeyRepository
import com.psg.domain.repository.LolRepository
import com.psg.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule{
    @Provides
    @Singleton
    fun provideSummonerUseCase(repository: LolRepository) = GetSummonerUseCase(repository)
    @Provides
    @Singleton
    fun provideSpectatorInfoBUseCase(repository: LolRepository) = GetSpectatorInfoBUseCase(repository)
    @Provides
    @Singleton
    fun provideSpectatorInfoRUseCase(repository: LolRepository) = GetSpectatorInfoRUseCase(repository)
    @Provides
    @Singleton
    fun provideSpectatorUseCase(repository: LolRepository) = GetSpectatorUseCase(repository)
    @Provides
    @Singleton
    fun provideSearchUseCase(repository: LolRepository) = GetSearchUseCase(repository)
    @Provides
    @Singleton
    fun provideProfileUseCase(repository: LolRepository) = GetProfileUseCase(repository)
    @Provides
    @Singleton
    fun provideKeyUseCase(repository: KeyRepository) = GetKeyUseCase(repository)
    @Provides
    @Singleton
    fun provideInsertKeyUseCase(repository: KeyRepository) = InsertKeyUseCase(repository)
    @Provides
    @Singleton
    fun provideDeleteSummonerUseCase(repository: LolRepository) = DeleteSummonerUseCase(repository)
    @Provides
    @Singleton
    fun provideDeleteSummonerAllUseCase(repository: LolRepository) = DeleteSummonerAllUseCase(repository)
    @Provides
    @Singleton
    fun provideInsertProfileUseCase(repository: LolRepository) = InsertProfileUseCase(repository)
    @Provides
    @Singleton
    fun provideDeleteProfileUseCase(repository: LolRepository) = DeleteProfileUseCase(repository)
    @Provides
    @Singleton
    fun provideDeleteSearchUseCase(repository: LolRepository) = DeleteSearchUseCase(repository)
    @Provides
    @Singleton
    fun provideDeleteSearchAllUseCase(repository: LolRepository) = DeleteSearchAllUseCase(repository)
    @Provides
    @Singleton
    fun provideRefreshDataUseCase(repository: LolRepository) = RefreshDataUseCase(repository)
    @Provides
    @Singleton
    fun provideSearchLeagueUseCase(repository: LolRepository) = SearchLeagueUseCase(repository)
}