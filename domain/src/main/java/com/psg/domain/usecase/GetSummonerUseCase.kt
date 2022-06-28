package com.psg.domain.usecase

import com.psg.domain.model.Summoner
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class GetSummonerUseCase(private val repository: LolRepository) {
    suspend operator fun invoke(): Flow<List<Summoner>> = repository.getSummoner()
}