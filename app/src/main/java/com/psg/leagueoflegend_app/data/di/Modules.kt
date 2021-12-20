package com.psg.leagueoflegend_app.data.di

import com.psg.leagueoflegend_app.data.api.LeagueOfLegendAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://kr.api.riotgames.com/lol/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LeagueOfLegendAPI::class.java)
    }
}

val viewModelModule = module {

}

val repositoryModule = module {

}