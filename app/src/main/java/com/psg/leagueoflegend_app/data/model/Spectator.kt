package com.psg.leagueoflegend_app.data.model

data class Spectator(
    val map: String,
    val banChamp: List<BanChamp>
)
data class BanChamp(
    val team: String,
    val champ: String
)


