package com.psg.domain.usecase

import com.psg.domain.model.Profile
import com.psg.domain.repository.LolRepository

class InsertProfileUseCase(private val repository: LolRepository) {
    suspend operator fun invoke(profile: Profile){
        repository.insertProfile(profile)
    }
}