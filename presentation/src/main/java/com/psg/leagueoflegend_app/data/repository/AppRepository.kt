package com.psg.leagueoflegend_app.data.repository

import com.psg.leagueoflegend_app.di.LoLApp
import com.psg.data.api.LeagueOfLegendAPI
import com.psg.data.model.remote.CurrentGameInfo
import com.psg.data.model.remote.LeagueEntryDTO
import com.psg.data.model.remote.SummonerResponse
import com.psg.data.db.LoLDao
import retrofit2.Response

class AppRepository constructor(private val dao: LoLDao, private val api: LeagueOfLegendAPI) {

//    suspend fun searchSummoner(name: String, apiKey: String): Response<SummonerResponse> = api.getSummoner(name,apiKey)
//
//    suspend fun searchLeague(summonerId:String?, apiKey: String): Response<Set<LeagueEntryDTO>> = api.getLeague(summonerId,apiKey)
//
//    suspend fun searchSpectator(summonerId:String?, apiKey: String): Response<CurrentGameInfo> = api.getSpectator(summonerId,apiKey)




    // API KEY
    fun getApikey() = LoLApp.pref.getApikey()

    fun setApikey(value: String) {
        LoLApp.pref.setApikey(value)
    }

    fun delApikey() {
        LoLApp.pref.delApikey()
    }





}