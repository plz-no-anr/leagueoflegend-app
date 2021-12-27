package com.psg.leagueoflegend_app.view.main

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.utils.Constants
import com.psg.leagueoflegend_app.utils.TimeCheckService
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: AppRepository) : BaseViewModel() {

//    private val _eventFlow = MutableSharedFlow<Event>()
//    override val eventFlow: SharedFlow<BaseViewModel.Event> = _eventFlow.asSharedFlow()

    val summonerList: LiveData<List<SummonerEntity>> get() = repository.getSummoner()


//    override fun toastEvent(text: String){
//        event(SearchViewModel.Event.ShowToast(text))
//    }
//
//    private fun event(event: SearchViewModel.Event) {
//        CoroutineScope(Dispatchers.IO).launch{
//            _eventFlow.emit(event)
//        }
//    }

    fun refresh(list: List<SummonerEntity>) {
        val key = Constants.API_KEY
        if (list.isNotEmpty()) {
            for (summoner in list)
                CoroutineScope(Dispatchers.IO).launch {
                    val body = repository.searchSummoner(summoner.name, key).body()
                    val code = repository.searchSummoner(summoner.name, key).code()

                    if (code == 200 && body != null) {
                        val res = repository.searchLeague(body.id, key)
                        val iterator = res.body()?.iterator() ?: iterator { }
                        while (iterator.hasNext()) {
                            val league = iterator.next()
                            if (league.queueType == "RANKED_SOLO_5x5") {
                                println("소환사이름:${league.summonerName},티어:${league.tier},리그포인트${league.leaguePoints},랭크:${league.rank},전적:${league.wins}승,${league.losses}패")
                                if (league.miniSeries != null) {
                                    val mini = SummonerEntity.MiniSeries(
                                        league.miniSeries.losses!!,
                                        league.miniSeries.target!!,
                                        league.miniSeries.wins!!,
                                        league.miniSeries.progress!!
                                    )
                                    val icon =
                                        "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"

                                    repository.updateSummoner(
                                        SummonerEntity(
                                            league.summonerName!!,
                                            body.summonerLevel.toString(),
                                            icon,
                                            league.tier!!,
                                            league.leaguePoints!!,
                                            league.rank!!,
                                            league.wins!!,
                                            league.losses!!,
                                            mini
                                        )
                                    )
                                    println("승급전중")
                                    println(
                                        "승급전:${
                                            league.miniSeries.progress.replace("L", "패")
                                                .replace("W", "승")
                                        }"
                                    )

                                } else {
                                    val mini = SummonerEntity.MiniSeries(0, 0, 0, "No")
                                    val icon =
                                        "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"
                                    repository.updateSummoner(
                                        SummonerEntity(
                                            league.summonerName!!,
                                            body.summonerLevel.toString(),
                                            icon,
                                            league.tier!!,
                                            league.leaguePoints!!,
                                            league.rank!!,
                                            league.wins!!,
                                            league.losses!!,
                                            mini
                                        )
                                    )
                                    println("승급전아님")

                                }
                                toastEvent("업데이트 성공")
                            }
                        }
                    } else {
                        when(code){
                            403 -> toastEvent("토큰이 만료되었습니다.")
                            404 -> toastEvent("존재하지 않는 아이디입니다.")
                            429 -> println("너무 많은 요청")
                        }
                        println(
                            "리스폰스에러바디:${
                                repository.searchSummoner(summoner.name, key).errorBody()
                                    ?.string()
                            }"
                        )
                        println("에러코드:${repository.searchSummoner(summoner.name, key).code()}")
                        println("존재하지 않는 아이디")
                    }

                }
        }
    }



//    sealed class Event {
//        data class ShowToast(val text: String) : Event()
//    }


}