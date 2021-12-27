package com.psg.leagueoflegend_app.data.model

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.psg.leagueoflegend_app.R

@Entity
data class SummonerEntity(
    @PrimaryKey
    val name: String, // 소환사 이름
    val level: String, // 레벨
    val icon: String, // 소환사 아이콘
    val tier: String, // 티어 (GOLD)
    val leaguePoints: Int, // 점수
    val rank: String, // 랭크 (I)
    val wins: Int, // 승리
    val losses: Int, // 패배
    val miniSeries: MiniSeries? // 승급전
) {

    data class MiniSeries(
        var losses: Int,
        var target: Int,
        var wins: Int,
        var progress: String
    )

    fun getLevels() = "LV: $level"

    fun getLeaguePoint() = "$leaguePoints LP / ${wins}승 ${losses}패"

    fun getTierRank() = "$tier $rank"


    fun tierIcon()= when(tier){
        "IRON" -> R.drawable.emblem_iron
        "BRONZE" -> R.drawable.emblem_bronze
        "SILVER" -> R.drawable.emblem_silver
        "GOLD" -> R.drawable.emblem_gold
        "PLATINUM" -> R.drawable.emblem_platinum
        "DIAMOND" -> R.drawable.emblem_diamond
        "MASTER" -> R.drawable.emblem_grandmaster
        "GRANDMASTER" -> R.drawable.emblem_grandmaster
        "CHALLENGER" -> R.drawable.emblem_challenger
        else -> R.drawable.lol
    }




}
