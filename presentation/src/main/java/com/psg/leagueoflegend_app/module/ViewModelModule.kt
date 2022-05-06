package com.psg.leagueoflegend_app.module

import com.psg.leagueoflegend_app.view.main.MainViewModel
import com.psg.leagueoflegend_app.view.search.SearchViewModel
import com.psg.leagueoflegend_app.view.spectator.SpectatorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { SpectatorViewModel(get()) }
}