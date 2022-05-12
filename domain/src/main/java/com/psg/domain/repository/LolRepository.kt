package com.psg.domain.repository

import com.psg.domain.model.*
import kotlinx.coroutines.flow.Flow

interface LolRepository {
    suspend fun getSummoner(): Flow<List<Summoner>>
    suspend fun deleteSummoner(summoner: Summoner)
    suspend fun deleteSummonerAll()
    suspend fun getProfile(): Flow<Profile>
    suspend fun insertProfile(profile: Profile)
    suspend fun deleteProfile()
    suspend fun getSearch(): Flow<List<Search>>
    suspend fun deleteSearch(search: Search)
    suspend fun deleteSearchAll()
    suspend fun getSpectator(name: String): Flow<Spectator>
    suspend fun getSpectatorInfoR(name: String): Flow<List<SpectatorInfo>>
    suspend fun getSpectatorInfoB(name: String): Flow<List<SpectatorInfo>>
    fun searchLeague(name: String, key: String, date: String): Flow<League>
    fun refreshData(list: List<Summoner>): Flow<League>
}