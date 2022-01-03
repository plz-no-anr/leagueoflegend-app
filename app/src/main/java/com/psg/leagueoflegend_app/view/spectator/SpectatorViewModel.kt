package com.psg.leagueoflegend_app.view.spectator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.data.model.Spectator
import com.psg.leagueoflegend_app.data.model.SpectatorInfo
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class SpectatorViewModel(private val repository: AppRepository): BaseViewModel() {

    val spectatorList: LiveData<List<SpectatorInfo>> get() = _spectatorList
    private var _spectatorList = MutableLiveData<List<SpectatorInfo>>()

    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> get() = _apiKey

        init {
            _apiKey.value = repository.getApikey()
//            _spectatorList.value = getList()
        }

        private fun getList(name: String){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    apiKey.value?.let { key ->
                        val body = repository.searchSummoner(name, key).body()
                        val code = repository.searchSummoner(name, key).code()
                        if (code == 200) {
                            body?.id.let {
                                val res = repository.searchSpectator(it, key).body()
                                if (res != null) {
                                    val list = mutableListOf<Spectator>()
                                    for (i in res.participants) {
                                        list.add(
                                            Spectator(
                                                res.mapId,
                                                res.gameMode,
                                                res.gameType,
                                                i.teamId,
                                                i.spell1Id,
                                                i.spell2Id,
                                                i.championId,
                                                i.summonerName,
                                                i.perks
                                            )
                                        )
//                                    res.mapId
//                                    res.gameMode
//                                    res.gameType
//                                    i.teamId
//                                    i.spell1Id
//                                    i.spell2Id
//                                    i.championId
//                                    i.summonerName
//                                    i.perks
                                    }

                                }
                            }

                        } else {
                            when (code) {
                                401 -> toastEvent("토큰이 인증되지 않았습니다.")
                                403 -> toastEvent("토큰이 만료되었습니다.")
                                404 -> toastEvent("존재하지 않는 아이디입니다.")
                                else -> toastEvent("이번 시즌 전적이 존재하지 않습니다.")
                            }
                            println(
                                "리스폰스에러바디:${
                                    repository.searchSummoner(name, key).errorBody()?.string()
                                }"
                            )
                            println("에러코드:${repository.searchSummoner(name, key).code()}")
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }







    fun toJson(mapId:Long){
        val mapString = LoLApp.getContext().assets.open("map.json").reader().readText()
        val mapArray = JSONArray(mapString)
        for (i in 0 until mapArray.length()){
//                println("제이슨 어레이")mapArray.getJSONObject(i).getJSONArray("data")
        }
    }

}