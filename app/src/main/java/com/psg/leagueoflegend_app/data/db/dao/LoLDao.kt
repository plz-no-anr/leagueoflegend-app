package com.psg.leagueoflegend_app.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity

@Dao
interface LoLDao {
    @Query("SELECT * FROM SummonerEntity")
    fun getSummoner(): LiveData<List<SummonerEntity>>

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
    fun getSearch(): LiveData<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Delete
    suspend fun deleteSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM SearchEntity")
    suspend fun deleteSearchAll()

}