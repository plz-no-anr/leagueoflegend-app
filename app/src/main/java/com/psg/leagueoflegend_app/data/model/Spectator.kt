package com.psg.leagueoflegend_app.data.model

data class Spectator(
    val map: Long,
    val gameMode: String,
    val gameType: String,
    val team: Long,
    val spell1: Long,
    val spell2: Long,
    val champ: Long,
    val name: String,
    val perks: CurrentGameInfo.CurrentGameParticipant.Perks
)
