package com.psg.data.db

import androidx.room.*
import com.psg.data.model.local.ProfileEntity
import com.psg.data.model.local.SearchEntity
import com.psg.data.model.local.SummonerEntity

@Dao
interface LoLDao {
    @Query("SELECT * FROM SummonerEntity")
    fun getSummoner(): List<SummonerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    @Update
    suspend fun updateSummoner(summonerEntity: SummonerEntity)

    @Delete
    suspend fun deleteSummoner(summonerEntity: SummonerEntity)

    @Query("DELETE FROM SummonerEntity")
    suspend fun deleteSummonerAll()

    //Search

    @Query("SELECT * FROM SearchEntity")
    fun getSearch(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Delete
    suspend fun deleteSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM SearchEntity")
    suspend fun deleteSearchAll()

    @Query("SELECT * FROM ProfileEntity")
    fun getProfile(): ProfileEntity

    @Insert
    suspend fun insertProfile(profileEntity: ProfileEntity)

    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)

    @Query("DELETE FROM ProfileEntity")
    suspend fun deleteProfile()


}