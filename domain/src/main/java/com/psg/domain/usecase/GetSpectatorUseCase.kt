package com.psg.domain.usecase

import com.psg.domain.model.Spectator
import com.psg.domain.model.SpectatorInfo
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class GetSpectatorUseCase(private val repository: LolRepository) {
    suspend fun execute(name: String): Flow<Spectator> = repository.getSpectator(name)
}