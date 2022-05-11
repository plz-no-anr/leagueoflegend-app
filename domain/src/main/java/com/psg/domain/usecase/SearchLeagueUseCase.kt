package com.psg.domain.usecase

import com.psg.domain.model.League
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class SearchLeagueUseCase(
    private val repository: LolRepository
    ) {
    fun execute(name: String, key: String, date: String): Flow<League> = repository.searchLeague(name, key, date)
}