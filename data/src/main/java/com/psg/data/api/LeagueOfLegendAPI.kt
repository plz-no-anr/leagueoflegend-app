package com.psg.data.api

import com.psg.data.model.remote.SpectatorResponse
import com.psg.data.model.remote.LeagueResponse
import com.psg.data.model.remote.SummonerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeagueOfLegendAPI {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    suspend fun getSummoner(
        @Path("summonerName") summonerName: String,
        @Query("api_key") api_key: String
    ): Response<SummonerResponse>

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    suspend fun getLeague(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Response<Set<LeagueResponse>>

    @GET("spectator/v4/active-games/by-summoner/{encryptedSummonerId}")
    suspend fun getSpectator(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Response<SpectatorResponse>
}