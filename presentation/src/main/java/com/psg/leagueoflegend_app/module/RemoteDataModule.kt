package com.psg.leagueoflegend_app.module

import com.psg.data.repository.lol.remote.LolRemoteDataSource
import com.psg.data.repository.lol.remote.LolRemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataModule = module {
    single<LolRemoteDataSource> { LolRemoteDataSourceImpl(get()) }
}