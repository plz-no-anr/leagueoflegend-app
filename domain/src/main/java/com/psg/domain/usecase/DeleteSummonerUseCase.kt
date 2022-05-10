package com.psg.domain.usecase

import com.psg.domain.model.Summoner
import com.psg.domain.repository.LolRepository

class DeleteSummonerUseCase(private val repository: LolRepository) {
    suspend fun execute(summoner: Summoner){
        repository.deleteSummoner(summoner)
    }
}