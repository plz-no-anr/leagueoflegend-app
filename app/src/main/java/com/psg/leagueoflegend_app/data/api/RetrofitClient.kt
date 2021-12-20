package com.psg.leagueoflegend_app.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://kr.api.riotgames.com/lol/")
            .addConverterFactory(GsonConverterFactory.create())
    }



    val summonerService: LeagueOfLegendAPI by lazy {
        client.build().create(LeagueOfLegendAPI::class.java)
    }

}