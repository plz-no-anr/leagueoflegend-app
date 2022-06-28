package com.psg.domain.usecase

import com.psg.domain.model.Summoner
import com.psg.domain.repository.LolRepository

class DeleteSummonerUseCase(private val repository: LolRepository) {
    suspend operator fun invoke(summoner: Summoner){
        repository.deleteSummoner(summoner)
    }
}