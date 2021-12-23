package com.psg.leagueoflegend_app.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity

@Dao
interface LoLDao {
    @Query("SELECT * FROM SummonerEntity")
    fun getSummoner(): LiveData<List<SummonerEntity>>

    @Insert
    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    @Delete
    suspend fun deleteSummoner(summonerEntity: SummonerEntity)

    @Query("DELETE FROM SummonerEntity")
    suspend fun deleteSummonerAll()

    //Search

    @Query("SELECT * FROM SearchEntity")
    fun getSearch(): LiveData<List<SearchEntity>>

    @Insert
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Delete
    suspend fun deleteSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM SearchEntity")
    suspend fun deleteSearchAll()

}