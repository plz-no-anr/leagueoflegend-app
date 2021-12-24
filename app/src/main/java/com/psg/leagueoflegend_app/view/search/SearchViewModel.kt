package com.psg.leagueoflegend_app.view.search

import android.util.EventLog
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.data.model.LeagueEntryDTO
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.utils.Constants
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.LocalDate
class SearchViewModel(private val repository: AppRepository): BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    val searchList: LiveData<List<SearchEntity>> get() = repository.getSearch()


    private fun toastEvent(text: String){
        event(Event.ShowToast(text))
   }

    private fun event(event: Event) {
        CoroutineScope(Dispatchers.IO).launch{
            _eventFlow.emit(event)
        }
    }

    fun saveSummoner(name:String) {
        try {
            val date = LocalDate.now().toString()
            if (name.isNotEmpty()){
                searchLeague(name,Constants.API_KEY,date)
            } else{
                println("텍스트가 null")
                toastEvent("아이디를 입력해주세요.")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun searchLeague(name: String, key: String, date:String){
        CoroutineScope(Dispatchers.IO).launch {
            val body = repository.searchSummoner(name,key).body()
            if (body != null){
            val res = repository.searchLeague(body.id,key)
            val iterator = res.body()?.iterator() ?: iterator {  }
            while (iterator.hasNext()){
                val league = iterator.next()
                if (league.queueType == "RANKED_SOLO_5x5") {
                    println("소환사이름:${league.summonerName},티어:${league.tier},리그포인트${league.leaguePoints},랭크:${league.rank},전적:${league.wins}승,${league.losses}패")
                    if (league.miniSeries != null){
                        val mini = SummonerEntity.MiniSeries(league.miniSeries.losses!!,league.miniSeries.target!!,league.miniSeries.wins!!,league.miniSeries.progress!!)
                        repository.insertSummoner(SummonerEntity(league.summonerName!!,body.summonerLevel.toString(),body.profileIconId,league.tier!!,league.leaguePoints!!,league.rank!!,league.wins!!,league.losses!!,mini))
                        println("승급전중")
                        println("승급전:${league.miniSeries.progress.replace("L","패").replace("W","승")}")

                    }else{
                        val mini = SummonerEntity.MiniSeries(0,0,0,"No")
                        repository.insertSummoner(SummonerEntity(league.summonerName!!,body.summonerLevel.toString(),body.profileIconId,league.tier!!,league.leaguePoints!!,league.rank!!,league.wins!!,league.losses!!,mini))
                        println("승급전아님")

                    }
                    toastEvent("등록성공")
                    insertSearch(SearchEntity(league.summonerName,date))
                }
            }
            } else {
                toastEvent("존재하지 않는 아이디입니다.")
                println("존재하지 않는 아이디")
            }

        }
    }

    fun getAllSearch(): LiveData<List<SearchEntity>> {
        return searchList
    }

    private fun insertSearch(searchEntity: SearchEntity) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertSearch(searchEntity)
    }


    fun deleteSearch(searchEntity: SearchEntity)= CoroutineScope(Dispatchers.IO).launch {
        repository.deleteSearch(searchEntity)
    }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteSearchAll()
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
    }


}

