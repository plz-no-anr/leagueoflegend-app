package com.psg.domain.repository

import com.psg.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LolRepository {
    fun getSummoner(): Flow<List<Summoner>>
    fun getProfile(): Flow<Profile>
    fun getSearch(): Flow<List<Search>>
    fun getSpectator(): Flow<Spectator>
    fun getSpectatorInfo(): Flow<List<SpectatorInfo>>
}