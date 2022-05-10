package com.psg.domain.usecase

import com.psg.domain.repository.KeyRepository

class GetKeyUseCase(private val repository: KeyRepository) {
    fun execute(): String = repository.apiKey // API키
}