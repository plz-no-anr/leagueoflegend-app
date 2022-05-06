package com.psg.data.repository.lol.remote

import com.psg.data.api.LeagueOfLegendAPI
import com.psg.data.model.remote.CurrentGameInfo
import com.psg.data.model.remote.LeagueEntryDTO
import com.psg.data.model.remote.SummonerResponse

class LolRemoteDataSourceImpl(private val api: LeagueOfLegendAPI):
LolRemoteDataSource {
    override suspend fun searchSummoner(name: String, apiKey: String): SummonerResponse {
        return api.getSummoner(name, apiKey)
    }

    override suspend fun searchLeague(summonerId: String?, apiKey: String): Set<LeagueEntryDTO> {
        return api.getLeague(summonerId, apiKey)
    }

    override suspend fun searchSpectator(summonerId: String?, apiKey: String): CurrentGameInfo {
        return api.getSpectator(summonerId, apiKey)
    }
}