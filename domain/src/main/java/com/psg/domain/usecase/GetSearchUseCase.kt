package com.psg.domain.usecase

import com.psg.domain.model.Search
import com.psg.domain.repository.LolRepository
import kotlinx.coroutines.flow.Flow

class GetSearchUseCase(private val repository: LolRepository) {
    suspend operator fun invoke(): Flow<List<Search>> = repository.getSearch()
}