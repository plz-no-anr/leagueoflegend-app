package com.psg.leagueoflegend_app.data.di

import androidx.room.Room
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.data.api.LeagueOfLegendAPI
import com.psg.leagueoflegend_app.data.db.AppDatabase
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.utils.MiniSeriesTypeConverter
import com.psg.leagueoflegend_app.view.main.MainViewModel
import com.psg.leagueoflegend_app.view.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://kr.api.riotgames.com/lol/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LeagueOfLegendAPI::class.java)
    }
    single {
        Room.databaseBuilder(
        androidApplication(),
        AppDatabase::class.java,
        "lol-app.db")
            .addTypeConverter(MiniSeriesTypeConverter(LoLApp.getGson()))
        .build()
    }
    single { get<AppDatabase>().LoLDao() }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

val repositoryModule = module {
    single { AppRepository(get(),get()) }
}