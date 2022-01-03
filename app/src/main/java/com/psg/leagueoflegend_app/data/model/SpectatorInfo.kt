package com.psg.leagueoflegend_app.data.model

data class SpectatorInfo(
    val teamId: Long,
    val champImage: String,
    val spell1: Long,
    val spell2: Long,
    val rune1: String,
    val rune2: String,
    val name: String,
    val champMatch: String,
    val tier: String
)
