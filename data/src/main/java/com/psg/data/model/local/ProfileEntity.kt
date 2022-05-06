package com.psg.data.model.local

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
