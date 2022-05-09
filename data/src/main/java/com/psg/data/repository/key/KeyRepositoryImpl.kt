package com.psg.data.repository.key

import com.psg.data.repository.key.local.KeyLocalDataSource
import com.psg.domain.repository.KeyRepository

class KeyRepositoryImpl(private val keyLocalDataSource: KeyLocalDataSource) : KeyRepository {

    override var apiKey: String
        get() = keyLocalDataSource.apiKey
        set(value) {
            keyLocalDataSource.apiKey = value
        }
}