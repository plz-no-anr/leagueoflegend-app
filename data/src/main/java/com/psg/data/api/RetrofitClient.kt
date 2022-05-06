package com.psg.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val BASE_URL = "https://kr.api.riotgames.com/lol/"
    private val client: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }



    val summonerService: LeagueOfLegendAPI by lazy {
        client.build().create(LeagueOfLegendAPI::class.java)
    }

}