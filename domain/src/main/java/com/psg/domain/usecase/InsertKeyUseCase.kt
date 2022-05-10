package com.psg.domain.usecase

import com.psg.domain.repository.KeyRepository

class InsertKeyUseCase(private val repository: KeyRepository) {
    fun execute(value: String) {
        repository.apiKey = value
    }
}