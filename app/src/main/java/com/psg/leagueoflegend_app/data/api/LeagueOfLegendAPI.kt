package com.psg.leagueoflegend_app.data.api

import com.psg.leagueoflegend_app.data.model.LeagueEntryDTO
import com.psg.leagueoflegend_app.data.model.Summoner
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeagueOfLegendAPI {
    @GET("summoner/v4/summoners/by-name/{summonerName}")
    fun getSummoner(
        @Path("summonerName") summonerName: String,
        @Query("api_key") api_key: String
    ): Call<Summoner>

    @GET("league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getLeague(
        @Path("encryptedSummonerId") encryptedSummonerId: String?,
        @Query("api_key") api_key: String
    ): Call<Set<LeagueEntryDTO>>
}