package com.psg.domain.usecase

import com.psg.domain.repository.KeyRepository

class InsertKeyUseCase(private val repository: KeyRepository) {
    operator fun invoke(value: String) {
        repository.apiKey = value
    }
}