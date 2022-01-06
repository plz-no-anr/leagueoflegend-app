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
    val keyy = "RGAPI-32904622-1b3a-48f8-97c2-7e12615faf40"

        init {
            _apiKey.value = repository.getApikey()
//            _spectatorList.value = getList()
        }

        fun getList(name: String): List<Spectator> {
            val list = mutableListOf<Spectator>()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    apiKey.value?.let { key ->
                        val body = repository.searchSummoner(name,keyy).body()
                        val code = repository.searchSummoner(name, keyy).code()
                        if (code == 200) {
                            body?.id.let {
                                val res = repository.searchSpectator(it, keyy).body()
                                if (res != null) {
                                    jsonToMap(res.mapId)

                                    for (x in res.bannedChampions){
//                                        println("밴:$x")
//                                        println("밴한id:${x.championId}"
                                        println("밴한챔프:${jsonToChamp(x.championId)}")
                                    }
                                    for (i in res.participants) {
                                        println("픽한얘:${i.summonerName}")
                                        println("픽한챔프:${jsonToChamp(i.championId)}")
                                        jsonToSpell(i.spell1Id)
                                        jsonToSpell(i.spell2Id)
//                                        jsonToRuneStyle(i.perks.perkStyle)
//                                        jsonToRuneStyle(i.perks.perkSubStyle)

                                        for (x in jsonToRunes(i.perks.perkStyle,i.perks.perkSubStyle,i.perks.perkIds)){
                                            println("룬$x")
                                        }
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
                                    repository.searchSummoner(name, keyy).errorBody()?.string()
                                }"
                            )
                            println("에러코드:${repository.searchSummoner(name, keyy).code()}")
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            return list
        }







    private fun jsonToMap(mapId:Long):String{
        var mapName = ""
        val mapString = LoLApp.getContext().assets.open("map.json").reader().readText()
        val mapArray = JSONArray(mapString)
        for (i in 0 until mapArray.length()){
                println("맵:${mapArray.getJSONObject(i).getJSONObject("data").getJSONObject(mapId.toString()).getString("MapName")}")
            mapName = mapArray.getJSONObject(i).getJSONObject("data").getJSONObject(mapId.toString()).getString("MapName")
        }
        return mapName
    }

    private fun jsonToChamp(champId:Long):String{
        var champName = ""
        if (champId == (-1).toLong()){
            champName = "NoBan"
            return champName
        }
        val champString = LoLApp.getContext().assets.open("champion.json").reader().readText()
        val champArr = JSONArray(champString)
        for (i in 0 until champArr.length()){
//            println("챔프:${champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString()).getString("name")}")
            champName = champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString()).getString("name")
        }
        return champName
    }

    private fun jsonToRuneStyle(perkStyle:Long){
        var main = ""
        var sub = ""
        var runeName = ""

        val runeString = LoLApp.getContext().assets.open("runesReforged.json").reader().readText()
        val runeArray = JSONArray(runeString)
        for (i in 0 until  runeArray.length()){
            if (runeArray.getJSONObject(i).getString("id") == perkStyle.toString()) {
                println("룬:${runeArray.getJSONObject(i).getString("name")}")
            }
        }
    }

    private fun jsonToRunes(perkStyle:Long,subStyle:Long, perks:List<Long>): List<String> {
        val runeNames = MutableList(6){""}
        val runeString = LoLApp.getContext().assets.open("runesReforged.json").reader().readText()
        val runeArray = JSONArray(runeString)
        for (i in perks.indices){
            if (i < 4) {
                for (j in 0 until runeArray.length()) {
                    if (runeArray.getJSONObject(j).getString("id") == perkStyle.toString()) {
                        val arr = runeArray.getJSONObject(j).getJSONArray("slots")
                        for (z in 0 until arr.length()) {
                            val rArr = arr.getJSONObject(z).getJSONArray("runes")
                            for (x in 0 until rArr.length()){
                                if (rArr.getJSONObject(x).getString("id") == perks[i].toString()){
//                                    println("룬:${rArr.getJSONObject(x).getString("name")}")
                                    runeNames[i] = rArr.getJSONObject(x).getString("name")
                                }

                            }
                        }

                    }
                }
            } else{
                if (i < 6){
                    for (j in 0 until runeArray.length()) {
                        if (runeArray.getJSONObject(j).getString("id") == subStyle.toString()) {
                            val arr = runeArray.getJSONObject(j).getJSONArray("slots")
                            for (z in 0 until arr.length()) {
                                val rArr = arr.getJSONObject(z).getJSONArray("runes")
                                for (x in 0 until rArr.length()){
                                    if (rArr.getJSONObject(x).getString("id") == perks[i].toString()){
//                                        println("보조룬:${rArr.getJSONObject(x).getString("name")}")
                                        runeNames[i] = rArr.getJSONObject(x).getString("name")
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }
        return runeNames
    }

    private fun jsonToSpell(spellId:Long):String{
        var spellName = ""
        val spellString = LoLApp.getContext().assets.open("summoner.json").reader().readText()
        val spellArray = JSONArray(spellString)
        for (i in 0 until spellArray.length()){
            println("스펠:${spellArray.getJSONObject(i).getJSONObject("data").getJSONObject(spellId.toString()).getString("name")}")
            spellName = spellArray.getJSONObject(i).getJSONObject("data").getJSONObject(spellId.toString()).getString("name")
        }
        return spellName
    }



}