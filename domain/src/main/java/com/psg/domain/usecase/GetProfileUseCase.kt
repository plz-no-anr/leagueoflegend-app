package com.psg.domain.usecase

import com.psg.domain.model.Profile
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(private val repository: LolRepository) {
    suspend fun execute():Flow<Profile> = repository.getProfile()
}