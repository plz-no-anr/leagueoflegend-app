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
     fun insertSummoner(summonerEntity: SummonerEntity)

    @Update
     fun updateSummoner(summonerEntity: SummonerEntity)

    @Delete
     fun deleteSummoner(summonerEntity: SummonerEntity)

    @Query("DELETE FROM SummonerEntity")
     fun deleteSummonerAll()

    //Search

    @Query("SELECT * FROM SearchEntity")
    fun getSearch(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertSearch(searchEntity: SearchEntity)

    @Delete
     fun deleteSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM SearchEntity")
     fun deleteSearchAll()

    @Query("SELECT * FROM ProfileEntity")
    fun getProfile(): ProfileEntity

    @Insert
     fun insertProfile(profileEntity: ProfileEntity)

    @Update
     fun updateProfile(profileEntity: ProfileEntity)

    @Query("DELETE FROM ProfileEntity")
     fun deleteProfile()


}