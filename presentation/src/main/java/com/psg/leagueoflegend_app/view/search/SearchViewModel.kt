package com.psg.leagueoflegend_app.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.psg.domain.model.League
import com.psg.domain.model.Search
import com.psg.domain.usecase.*
import com.psg.leagueoflegend_app.base.BaseViewModel
import com.psg.leagueoflegend_app.utils.AppLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val keyUseCase: GetKeyUseCase,
    private val searchUseCase: GetSearchUseCase,
    private val searchLeagueUseCase: SearchLeagueUseCase,
    private val deleteSearchUseCase: DeleteSearchUseCase,
    private val deleteSearchAllUseCase: DeleteSearchAllUseCase
    ) : BaseViewModel() {

    val searchList: LiveData<List<Search>> get() = _searchList
    private val _searchList = MutableLiveData<List<Search>>()

    val league: LiveData<League> get() = _league
    private val _league = MutableLiveData<League>()

    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> get() = _apiKey

    init {
        _apiKey.value = keyUseCase()

    }

    override fun initViewModel(){
        searchUpdate()
    }

     fun searchUpdate(){
        viewModelScope.launch {
            var searchList = listOf<Search>()
                searchUseCase().collect {
                    searchList = it
                }
            _searchList.value = searchList
        }
    }

    fun saveSummoner(name: String) {
        try {
            val date = LocalDate.now().toString()
            if (name.isNotEmpty()) {
                apiKey.value?.let {
                    viewModelScope.launch {
                        var league: League? = null
                            searchLeagueUseCase(name, it, date).collect {
                                league = it
                            }
                         _league.value = league!!
                    }
                }

            } else {
                AppLogger.p("텍스트가 null")
                toastEvent("아이디를 입력해주세요.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun searchLeague(name: String, key: String, date: String) = searchLeagueUseCase(name, key, date)


    fun deleteSearch(search: Search) = CoroutineScope(Dispatchers.IO).launch {
        deleteSearchUseCase(search)
        searchUpdate()
    }

    fun deleteAll() {
        CoroutineScope(Dispatchers.IO).launch {
            deleteSearchAllUseCase()
        }
        searchUpdate()
    }



}

