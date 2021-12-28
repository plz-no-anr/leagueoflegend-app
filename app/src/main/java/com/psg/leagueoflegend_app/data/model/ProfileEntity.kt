package com.psg.leagueoflegend_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(
    @PrimaryKey
    val name: String,
    val level: String,
    val icon: String
){
}
