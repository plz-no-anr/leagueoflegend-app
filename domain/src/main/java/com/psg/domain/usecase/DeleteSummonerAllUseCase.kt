package com.psg.domain.usecase

import com.psg.domain.repository.LolRepository

class DeleteSummonerAllUseCase(private val repository: LolRepository) {
    suspend operator fun invoke(){
        repository.deleteSummonerAll()
    }
}