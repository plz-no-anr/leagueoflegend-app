package com.psg.leagueoflegend_app.view.spectator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.psg.domain.model.Spectator
import com.psg.domain.model.SpectatorInfo
import com.psg.domain.usecase.GetKeyUseCase
import com.psg.domain.usecase.GetSpectatorInfoBUseCase
import com.psg.domain.usecase.GetSpectatorInfoRUseCase
import com.psg.domain.usecase.GetSpectatorUseCase
import com.psg.leagueoflegend_app.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpectatorViewModel @Inject constructor(
    private val keyUseCase: GetKeyUseCase,
    private val spectatorUseCase: GetSpectatorUseCase,
    private val spectatorInfoRUseCase: GetSpectatorInfoRUseCase,
    private val spectatorInfoBUseCase: GetSpectatorInfoBUseCase
) : BaseViewModel() {

    val spectatorListB: LiveData<List<SpectatorInfo>> get() = _spectatorListB
    private var _spectatorListB = MutableLiveData<List<SpectatorInfo>>()

    val spectatorListR: LiveData<List<SpectatorInfo>> get() = _spectatorListR
    private var _spectatorListR = MutableLiveData<List<SpectatorInfo>>()

    val spectator: LiveData<Spectator> get() = _spectator
    private var _spectator = MutableLiveData<Spectator>()

    private val _apiKey = MutableLiveData<String>()
    private val apiKey: LiveData<String> get() = _apiKey

    val isLoading: LiveData<Boolean> get() = _isLoading
    private var _isLoading = MutableLiveData<Boolean>()



    override fun initViewModel() {
        viewModelScope.launch {
            _apiKey.value = keyUseCase()
            _isLoading.value = false
        }
    }

    suspend fun setData(name: String) {
        viewModelScope.launch {

            spectatorUseCase(name).collect {
                _spectator.value = it
            }
            spectatorInfoBUseCase(name).collect {
                _spectatorListB.value = it
            }
            spectatorInfoRUseCase(name).collect {
                _spectatorListR.value = it
            }

            _isLoading.value = true

        }
    }

}