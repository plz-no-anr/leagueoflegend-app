package com.psg.leagueoflegend_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SummonerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int, // 프라이머리키
    var name: String, // 소환사 이름
    var tier: String, // 티어 (GOLD)
    var rank: String, // 랭크 (I)
    var wins: Int, // 승리
    var losses: Int, // 패배
    var miniSeries: MiniSeries? // 승급전
) {
    data class MiniSeries(
        var losses: Int,
        var target: Int,
        var wins: Int,
        var progress: String
    )
}
