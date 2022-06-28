package com.psg.domain.usecase

import com.psg.domain.model.SpectatorInfo
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class GetSpectatorInfoRUseCase(private val repository: LolRepository) {
    suspend operator fun invoke(name: String): Flow<List<SpectatorInfo>> = repository.getSpectatorInfoR(name)
}