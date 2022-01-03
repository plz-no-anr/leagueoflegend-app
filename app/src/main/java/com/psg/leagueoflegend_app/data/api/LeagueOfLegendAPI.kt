package com.psg.leagueoflegend_app.data.api

import com.psg.leagueoflegend_app.data.model.CurrentGameInfo
import com.psg.leagueoflegend_app.data.model.LeagueEntryDTO
import com.psg.leagueoflegend_app.data.model.Summoner
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeagueOfLegendAPI {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    suspend fun getSummoner(
        @Path("summonerName") summonerName: String,
        @Query("api_key") api_key: String
    ): Response<Summoner>

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    suspend fun getLeague(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Response<Set<LeagueEntryDTO>>

    @GET("spectator/v4/active-games/by-summoner/{encryptedSummonerId}")
    suspend fun getSpectator(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Response<CurrentGameInfo>
}