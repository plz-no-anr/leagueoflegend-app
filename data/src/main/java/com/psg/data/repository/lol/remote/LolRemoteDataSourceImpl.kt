package com.psg.data.repository.lol.remote

import com.psg.data.api.LeagueOfLegendAPI
import com.psg.data.model.remote.SpectatorResponse
import com.psg.data.model.remote.LeagueResponse
import com.psg.data.model.remote.SummonerResponse
import retrofit2.Response

class LolRemoteDataSourceImpl(private val api: LeagueOfLegendAPI):
LolRemoteDataSource {
    override suspend fun searchSummoner(name: String, apiKey: String): Response<SummonerResponse> {
        return api.getSummoner(name, apiKey)
    }

    override suspend fun searchLeague(summonerId: String?, apiKey: String): Response<Set<LeagueResponse>> {
        return api.getLeague(summonerId, apiKey)
    }

    override suspend fun searchSpectator(summonerId: String?, apiKey: String): Response<SpectatorResponse> {
        return api.getSpectator(summonerId, apiKey)
    }
}