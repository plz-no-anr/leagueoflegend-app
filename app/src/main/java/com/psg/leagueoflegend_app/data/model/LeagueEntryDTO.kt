package com.psg.leagueoflegend_app.data.model

data class LeagueEntryDTO(
    val leagueId: String? = null,
    val summonerId: String? = null,
    val summonerName: String? = null,
    val queueType: String? = null,
    val tier: String? = null,
    val rank: String? = null,
    val leaguePoints: Int? = null,
    val wins: Int? = null,
    val losses: Int? = null,
    val hotStreak: Boolean? = null,
    val veteran: Boolean? = null,
    val freshBlood: Boolean? = null,
    val inactive: Boolean? = null,
    val miniSeries: MiniSeriesDTO? = null

){
    override fun toString(): String {
        return "[wins = $wins, freshBlood = $freshBlood, summonerName = $summonerName, leaguePoints = $leaguePoints, losses = $losses, inactive = $inactive, tier = $tier, veteran = $veteran, leagueId = $leagueId, hotStreak = $hotStreak, queueType = $queueType, rank = $rank, summonerId = $summonerId]"
    }


    class MiniSeriesDTO(
        val losses: Int? = null,
        val target: Int? = null,
        val wins: Int? = null,
        val progress: String? = null
    )
}
