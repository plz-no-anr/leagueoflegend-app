package com.psg.data.repository.key.local

import com.psg.data.utils.PreferenceManager

class KeyLocalDataSourceImpl(private val preferenceManager: PreferenceManager):
KeyLocalDataSource{
    override var apiKey: String
    get() = preferenceManager.apiKey!!
    set(value) {
        preferenceManager.apiKey = value
    }
}