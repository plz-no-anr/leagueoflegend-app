package com.psg.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchEntity(
    @PrimaryKey
    val name: String,
    val date: String
)