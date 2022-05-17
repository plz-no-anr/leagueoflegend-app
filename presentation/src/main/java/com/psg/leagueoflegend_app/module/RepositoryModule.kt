package com.psg.leagueoflegend_app.module

import com.psg.data.repository.lol.LolRepositoryImpl
import com.psg.data.repository.key.KeyRepositoryImpl
import com.psg.domain.repository.KeyRepository
import com.psg.domain.repository.LolRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LolRepository> { LolRepositoryImpl(get(), get(),get(), get()) }
    single<KeyRepository> { KeyRepositoryImpl(get()) }
}