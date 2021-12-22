package com.psg.leagueoflegend_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SummonerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var tier: String,
    var wins: Int,
    var losses: Int,
    var miniSeries: MiniSeries
){
    data class MiniSeries(
        var losses: Int,
        var target: Int,
        var wins: Int,
        var progress: String
    )
}
