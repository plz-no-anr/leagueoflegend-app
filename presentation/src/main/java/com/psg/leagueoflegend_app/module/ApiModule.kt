package com.psg.leagueoflegend_app.module

import com.psg.data.api.LeagueOfLegendAPI
import com.psg.data.api.RetrofitClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LeagueOfLegendAPI::class.java)
    }

}