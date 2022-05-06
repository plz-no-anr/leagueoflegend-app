package com.psg.data.repository.lol.local

import com.psg.data.model.local.ProfileEntity
import com.psg.data.model.local.SearchEntity
import com.psg.data.model.local.SummonerEntity
import kotlinx.coroutines.flow.Flow

interface LolLocalDataSource {
    // Room Main
    fun getSummoner() : Flow<List<SummonerEntity>>

    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    suspend fun updateSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummoner(summonerEntity: SummonerEntity)

    suspend fun deleteSummonerAll()

    // Search
    fun getSearch() : Flow<List<SearchEntity>>

    suspend fun insertSearch(searchEntity: SearchEntity)

    suspend fun deleteSearch(searchEntity: SearchEntity)

    suspend fun deleteSearchAll()

    // Profile
    fun getProfile() : Flow<ProfileEntity>

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun updateProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

}