package com.psg.leagueoflegend_app.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.view.base.BaseViewModel

class SearchViewModel(private val repository: AppRepository): BaseViewModel() {
    private val _searchList = MutableLiveData<List<SearchEntity>>()
    var searchList: LiveData<List<SearchEntity>> get() = _searchList

    init {
        searchList = repository.getSearch()

    }

}