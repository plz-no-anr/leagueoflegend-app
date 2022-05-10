package com.psg.leagueoflegend_app.module

import com.psg.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    single { GetSummonerUseCase(get()) }
    single { GetSpectatorInfoBUseCase(get()) }
    single { GetSpectatorInfoRUseCase(get()) }
    single { GetSpectatorUseCase(get()) }
    single { GetSearchUseCase(get()) }
    single { GetProfileUseCase(get()) }
    single { GetKeyUseCase(get()) }
    single { DeleteSummonerUseCase(get()) }
    single { DeleteSummonerAllUseCase(get()) }
    single { InsertProfileUseCase(get()) }
    single { DeleteProfileUseCase(get()) }
    single { DeleteSearchUseCase(get()) }
    single { DeleteSearchAllUseCase(get()) }
}