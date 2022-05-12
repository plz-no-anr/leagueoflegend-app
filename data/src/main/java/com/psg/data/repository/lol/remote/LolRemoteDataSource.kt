package com.psg.data.repository.lol.remote

import com.psg.data.model.remote.CurrentGameInfo
import com.psg.data.model.remote.LeagueEntryDTO
import com.psg.data.model.remote.SummonerResponse
import retrofit2.Response

interface LolRemoteDataSource {

    suspend fun searchSummoner(name: String, apiKey: String): Response<SummonerResponse>

    suspend fun searchLeague(summonerId:String?, apiKey: String): Response<Set<LeagueEntryDTO>>

    suspend fun searchSpectator(summonerId:String?, apiKey: String): Response<CurrentGameInfo>

}