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

//    private val _eventFlow = MutableSharedFlow<Event>()
//    val eventFlow = _eventFlow.asSharedFlow()

    val searchList: LiveData<List<SearchEntity>> get() = repository.getSearch()

    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> get() = _apiKey

    init {
        _apiKey.value = repository.getApikey()
    }

//    private fun toastEvent(text: String){
//        event(Event.ShowToast(text))
//   }
//
//    private fun event(event: Event) {
//        CoroutineScope(Dispatchers.IO).launch{
//            _eventFlow.emit(event)
//        }
//    }

    fun saveSummoner(name:String) {
        try {
            val date = LocalDate.now().toString()
            if (name.isNotEmpty()){
                apiKey.value?.let {
                    searchLeague(name,it,date)
                }

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
            try {
            val body = repository.searchSummoner(name,key).body()
            val code = repository.searchSummoner(name,key).code()

            if (code == 200 && body != null){
            val res = repository.searchLeague(body.id,key)
            val iterator = res.body()?.iterator() ?: iterator {  }
            while (iterator.hasNext()){
                val league = iterator.next()
                if (league.queueType == "RANKED_SOLO_5x5") {
                    println("소환사이름:${league.summonerName},티어:${league.tier},리그포인트${league.leaguePoints},랭크:${league.rank},전적:${league.wins}승,${league.losses}패")
                    if (league.miniSeries != null){
                        val mini = SummonerEntity.MiniSeries(league.miniSeries.losses!!,league.miniSeries.target!!,league.miniSeries.wins!!,league.miniSeries.progress!!)
                        val icon = "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"

                        repository.insertSummoner(SummonerEntity(league.summonerName!!,body.summonerLevel.toString(),icon,league.tier!!,league.leaguePoints!!,league.rank!!,league.wins!!,league.losses!!,mini))
                        println("승급전중")
                        println("승급전:${league.miniSeries.progress.replace("L","패").replace("W","승")}")

                    }else{
                        val mini = SummonerEntity.MiniSeries(0,0,0,"No")
                        val icon = "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"
                        repository.insertSummoner(SummonerEntity(league.summonerName!!,body.summonerLevel.toString(),icon,league.tier!!,league.leaguePoints!!,league.rank!!,league.wins!!,league.losses!!,mini))
                        println("승급전아님")

                    }
                    toastEvent("등록성공")
                    insertSearch(SearchEntity(league.summonerName,date))
                }
            }
            } else {
                when(code){
                    401 -> toastEvent("토큰이 인증되지 않았습니다.")
                    403 -> toastEvent("토큰이 만료되었습니다.")
                    404 -> toastEvent("존재하지 않는 아이디입니다.")
                }
                println("리스폰스에러바디:${repository.searchSummoner(name,key).errorBody()?.string()}")
                println("에러코드:${repository.searchSummoner(name,key).code()}")
//                toastEvent("존재하지 않는 아이디입니다.")
                println("에러")
            }
            } catch (e : Exception){
                e.printStackTrace()
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




//    sealed class Event {
//        data class ShowToast(val text: String) : Event()
//    }


}

