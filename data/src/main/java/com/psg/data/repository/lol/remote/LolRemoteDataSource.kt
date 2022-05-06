package com.psg.data.repository.lol.remote

import com.psg.data.model.remote.CurrentGameInfo
import com.psg.data.model.remote.LeagueEntryDTO
import com.psg.data.model.remote.SummonerResponse

interface LolRemoteDataSource {

    suspend fun searchSummoner(name: String, apiKey: String): SummonerResponse

    suspend fun searchLeague(summonerId:String?, apiKey: String): Set<LeagueEntryDTO>

    suspend fun searchSpectator(summonerId:String?, apiKey: String): CurrentGameInfo

}