package com.psg.domain.model

data class SpectatorInfo(
    val name: String,
    val champName: String,
    val champImg: String,
    val team: String,
    val spell1: String,
    val spell2: String,
    val runeStyle: Rune,
    val subStyle: Rune,
    val mainRune: String,
    val rune: List<Rune>
){
    data class Rune(
        val name:String,
        val icon:String
    )
}

