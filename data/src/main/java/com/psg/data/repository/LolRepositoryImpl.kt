package com.psg.data.repository

import com.psg.data.repository.lol.local.LolLocalDataSource
import com.psg.data.repository.lol.remote.LolRemoteDataSource
import com.psg.domain.model.*
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class LolRepositoryImpl(
    private val lolRemoteDataSource:LolRemoteDataSource,
    private val lolLocalDataSource: LolLocalDataSource
): LolRepository {
    override fun getSummoner(): Flow<List<Summoner>> {
        TODO("Not yet implemented")
    }

    override fun getProfile(): Flow<Profile> {
        TODO("Not yet implemented")
    }

    override fun getSearch(): Flow<List<Search>> {
        TODO("Not yet implemented")
    }

    override fun getSpectator(): Flow<Spectator> {
        TODO("Not yet implemented")
    }

    override fun getSpectatorInfo(): Flow<List<SpectatorInfo>> {
        TODO("Not yet implemented")
    }
}