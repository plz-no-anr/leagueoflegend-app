package com.psg.domain.usecase

import com.psg.domain.model.League
import com.psg.domain.model.Summoner
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class RefreshDataUseCase(
    private val repository: LolRepository
) {
    fun execute(list: List<Summoner>): Flow<League> = repository.refreshData(list)
}