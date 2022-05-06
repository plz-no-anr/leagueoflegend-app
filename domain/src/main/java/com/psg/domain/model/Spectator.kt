package com.psg.domain.model

data class Spectator(
    val map: String,
    val banChamp: List<BanChamp>
){
    data class BanChamp(
        val team: String,
        val champ: String
    )
}



