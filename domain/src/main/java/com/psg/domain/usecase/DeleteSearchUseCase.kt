package com.psg.domain.usecase

import com.psg.domain.model.Search
import com.psg.domain.repository.LolRepository

class DeleteSearchUseCase(private val repository: LolRepository) {
    suspend fun execute(search: Search) {
        repository.deleteSearch(search)
    }
}