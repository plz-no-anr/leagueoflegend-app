package com.psg.leagueoflegend_app.view.main

import androidx.lifecycle.LiveData
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.view.base.BaseViewModel

class MainViewModel(private val repository: AppRepository):BaseViewModel() {

    val summonerList: LiveData<List<SummonerEntity>> get() = repository.getSummoner()



}