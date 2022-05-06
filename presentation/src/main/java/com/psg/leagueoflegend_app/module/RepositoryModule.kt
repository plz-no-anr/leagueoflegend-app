package com.psg.leagueoflegend_app.module

import com.psg.leagueoflegend_app.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(),get()) }
}