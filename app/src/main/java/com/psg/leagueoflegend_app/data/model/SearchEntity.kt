package com.psg.leagueoflegend_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int, // 프라이머리키
    val name: String,
    val date: String
)
