package com.psg.domain.usecase

import com.psg.domain.repository.KeyRepository

class GetKeyUseCase(private val repository: KeyRepository) {
    operator fun invoke(): String = repository.apiKey // API키
}