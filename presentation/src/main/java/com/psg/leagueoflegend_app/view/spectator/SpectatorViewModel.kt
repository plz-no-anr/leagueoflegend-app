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
            _apiKey.value = keyUseCase.execute()
            _isLoading.value = false
        }
    }

    suspend fun setData(name: String) {
//        var spectator: Spectator
//        var spectatorListB: List<SpectatorInfo>
//        var spectatorListR: List<SpectatorInfo>
        viewModelScope.launch {
//            withContext(Dispatchers.Default) {
//                spectator = getSpectator(name)
//                spectatorListB = getListB(name)
//                spectatorListR = getListR(name)
//            }

            spectatorUseCase.execute(name).collect {
                _spectator.value = it
            }
            spectatorInfoBUseCase.execute(name).collect {
                _spectatorListB.value = it
            }
            spectatorInfoRUseCase.execute(name).collect {
                _spectatorListR.value = it
            }

            _isLoading.value = true
//            _spectator.value = spectator
//            _spectatorListB.value = spectatorListB
//            _spectatorListR.value = spectatorListR

        }
    }

//    private suspend fun getSpectator(name: String): Spectator {
//        var spectator = Spectator("", mutableListOf())
//        val list = mutableListOf<Spectator.BanChamp>()
//        try {
//            apiKey.value?.let { key ->
//                val body = repository.searchSummoner(name, key).body()
//                val code = repository.searchSummoner(name, key).code()
//                if (code == 200) {
//                    body?.id.let {
//                        val res = repository.searchSpectator(it, key).body()
//                        if (res != null) {
//
//                            for (x in res.bannedChampions) {
//                                list.add(
//                                    Spectator.BanChamp(
//                                        longToTeam(x.teamId),
//                                        jsonToChampPath(x.championId)
//                                    )
//                                )
//                            }
//                            spectator = Spectator(
//                                jsonToMap(res.mapId),
//                                list
//                            )
//
//                        }
//                    }
//
//                } else {
//                    when (code) {
//                        401 -> toastEvent("토큰이 인증되지 않았습니다.")
//                        403 -> toastEvent("토큰이 만료되었습니다.")
//                        404 -> toastEvent("존재하지 않는 아이디입니다.")
//                        else -> toastEvent("이번 시즌 전적이 존재하지 않습니다.")
//                    }
//                    AppLogger.p(
//                        "리스폰스에러바디:${
//                            repository.searchSummoner(name, key).errorBody()?.string()
//                        }"
//                    )
//                    AppLogger.p("에러코드:${repository.searchSummoner(name, key).code()}")
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return spectator
//    }

//    private suspend fun getListB(name: String): List<SpectatorInfo> {
//        val list = mutableListOf<SpectatorInfo>()
//        try {
//            apiKey.value?.let { key ->
//                val body = repository.searchSummoner(name, key).body()
//                val code = repository.searchSummoner(name, key).code()
//                if (code == 200) {
//                    body?.id.let {
//                        val res = repository.searchSpectator(it, key).body()
//                        if (res != null) {
//                            for (i in res.participants) {
//                                if (longToTeam(i.teamId) == "블루") {
//                                    AppLogger.p("블루팀 추가")
//                                    list.add(
//                                        SpectatorInfo(
//                                            i.summonerName,
//                                            jsonToChampName(i.championId),
//                                            jsonToChampPath(i.championId),
//                                            longToTeam(i.teamId),
//                                            jsonToSpell(i.spell1Id),
//                                            jsonToSpell(i.spell2Id),
//                                            jsonToRuneStyle(i.perks.perkStyle),
//                                            jsonToRuneStyle(i.perks.perkSubStyle),
//                                            jsonToMainRunes(i.perks.perkStyle, i.perks.perkIds[0]),
//                                            jsonToRunes(
//                                                i.perks.perkStyle,
//                                                i.perks.perkSubStyle,
//                                                i.perks.perkIds
//                                            ),
//                                        )
//                                    )
//                                }
//
//                            }
//
//                        }
//                    }
//
//                } else {
//                    when (code) {
//                        401 -> toastEvent("토큰이 인증되지 않았습니다.")
//                        403 -> toastEvent("토큰이 만료되었습니다.")
//                        404 -> toastEvent("존재하지 않는 아이디입니다.")
//                        else -> toastEvent("이번 시즌 전적이 존재하지 않습니다.")
//                    }
//                    AppLogger.p(
//                        "리스폰스에러바디:${
//                            repository.searchSummoner(name, key).errorBody()?.string()
//                        }"
//                    )
//                    AppLogger.p("에러코드:${repository.searchSummoner(name, key).code()}")
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return list
//    }


//    private suspend fun getListR(name: String): List<SpectatorInfo> {
//        val list = mutableListOf<SpectatorInfo>()
//        try {
//            apiKey.value?.let { key ->
//                val body = repository.searchSummoner(name, key).body()
//                val code = repository.searchSummoner(name, key).code()
//                if (code == 200) {
//                    body?.id.let {
//                        val res = repository.searchSpectator(it, key).body()
//                        if (res != null) {
//                            for (i in res.participants) {
//                                if (longToTeam(i.teamId) == "레드") {
//                                    AppLogger.p("레드팀 추가")
//                                    list.add(
//                                        SpectatorInfo(
//                                            i.summonerName,
//                                            jsonToChampName(i.championId),
//                                            jsonToChampPath(i.championId),
//                                            longToTeam(i.teamId),
//                                            jsonToSpell(i.spell1Id),
//                                            jsonToSpell(i.spell2Id),
//                                            jsonToRuneStyle(i.perks.perkStyle),
//                                            jsonToRuneStyle(i.perks.perkSubStyle),
//                                            jsonToMainRunes(i.perks.perkStyle, i.perks.perkIds[0]),
//                                            jsonToRunes(
//                                                i.perks.perkStyle,
//                                                i.perks.perkSubStyle,
//                                                i.perks.perkIds
//                                            ),
//                                        )
//                                    )
//                                }
//
//
//                            }
//
//                        }
//                    }
//
//                } else {
//                    when (code) {
//                        401 -> toastEvent("토큰이 인증되지 않았습니다.")
//                        403 -> toastEvent("토큰이 만료되었습니다.")
//                        404 -> toastEvent("존재하지 않는 아이디입니다.")
//                        else -> toastEvent("이번 시즌 전적이 존재하지 않습니다.")
//                    }
//                    AppLogger.p(
//                        "리스폰스에러바디:${
//                            repository.searchSummoner(name, key).errorBody()?.string()
//                        }"
//                    )
//                    AppLogger.p("에러코드:${repository.searchSummoner(name, key).code()}")
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return list
//    }


//    private fun jsonToMap(mapId: Long): String {
//        var mapName = ""
//        val mapString = LoLApp.getContext().assets.open("map.json").reader().readText()
//        val mapArray = JSONArray(mapString)
//        for (i in 0 until mapArray.length()) {
//            AppLogger.p(
//                "맵:${
//                    mapArray.getJSONObject(i).getJSONObject("data").getJSONObject(mapId.toString())
//                        .getString("MapName")
//                }"
//            )
//            mapName =
//                mapArray.getJSONObject(i).getJSONObject("data").getJSONObject(mapId.toString())
//                    .getString("MapName")
//        }
//        return mapName
//    }
//
//    private fun jsonToChampPath(champId: Long): String {
//        var champName = ""
//        if (champId == (-1).toLong()) {
//            champName = "NoBan"
//            return champName
//        }
//        val champString = LoLApp.getContext().assets.open("champion.json").reader().readText()
//        val champArr = JSONArray(champString)
//        for (i in 0 until champArr.length()) {
////            AppLogger.p("챔프:${champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString()).getString("name")}")
//            val name =
//                champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString())
//                    .getJSONObject("image").getString("full")
//            champName = "http://ddragon.leagueoflegends.com/cdn/12.1.1/img/champion/$name"
//        }
//        return champName
//    }
//
//    private fun jsonToChampName(champId: Long): String {
//        var champName = ""
//        if (champId == (-1).toLong()) {
//            champName = "NoBan"
//            return champName
//        }
//        val champString = LoLApp.getContext().assets.open("champion.json").reader().readText()
//        val champArr = JSONArray(champString)
//        for (i in 0 until champArr.length()) {
////            AppLogger.p("챔프:${champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString()).getString("name")}")
//            champName =
//                champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString())
//                    .getString("name")
//        }
//        return champName
//    }
//
//    private fun jsonToRuneStyle(perkStyle: Long): Rune {
//        var style = Rune("", "")
//
//        val runeString = LoLApp.getContext().assets.open("runesReforged.json").reader().readText()
//        val runeArray = JSONArray(runeString)
//        for (i in 0 until runeArray.length()) {
//            if (runeArray.getJSONObject(i).getString("id") == perkStyle.toString()) {
//                AppLogger.p("룬:${runeArray.getJSONObject(i).getString("icon")}")
//                style = Rune(
//                    runeArray.getJSONObject(i).getString("name"),
//                    runeArray.getJSONObject(i).getString("icon")
//                )
//            }
//        }
//        return style
//    }
//
//    private fun jsonToMainRunes(perkStyle: Long, perks: Long): String {
//        var runeName = ""
//        val runeString = LoLApp.getContext().assets.open("runesReforged.json").reader().readText()
//        val runeArray = JSONArray(runeString)
//        for (j in 0 until runeArray.length()) {
//            if (runeArray.getJSONObject(j).getString("id") == perkStyle.toString()) {
//                val arr = runeArray.getJSONObject(j).getJSONArray("slots")
//                val rArr = arr.getJSONObject(0).getJSONArray("runes")
//                for (y in 0 until rArr.length()) {
//                    if (rArr.getJSONObject(y).getString("id") == perks.toString()) {
//                        AppLogger.p("룬2:${rArr.getJSONObject(y).getString("icon")}")
//                        runeName = rArr.getJSONObject(y).getString("icon")
//                    }
//                }
//
//
//            }
//        }
//        return runeName
//    }
//
//    private fun jsonToRunes(perkStyle: Long, subStyle: Long, perks: List<Long>): List<Rune> {
//        val runeNames = MutableList(6) { Rune("", "") }
//        val runeString = LoLApp.getContext().assets.open("runesReforged.json").reader().readText()
//        val runeArray = JSONArray(runeString)
//        for (i in perks.indices) {
//            if (i < 4) {
//                for (j in 0 until runeArray.length()) {
//                    if (runeArray.getJSONObject(j).getString("id") == perkStyle.toString()) {
//                        val arr = runeArray.getJSONObject(j).getJSONArray("slots")
//                        for (z in 0 until arr.length()) {
//                            val rArr = arr.getJSONObject(z).getJSONArray("runes")
//                            for (x in 0 until rArr.length()) {
//                                if (rArr.getJSONObject(x).getString("id") == perks[i].toString()) {
//                                    AppLogger.p("룬3:${rArr.getJSONObject(x).getString("icon")}")
//                                    runeNames[i] = Rune(
//                                        rArr.getJSONObject(x).getString("name"),
//                                        rArr.getJSONObject(x).getString("icon")
//                                    )
//                                }
//
//                            }
//                        }
//
//                    }
//                }
//            } else {
//                if (i < 6) {
//                    for (j in 0 until runeArray.length()) {
//                        if (runeArray.getJSONObject(j).getString("id") == subStyle.toString()) {
//                            val arr = runeArray.getJSONObject(j).getJSONArray("slots")
//                            for (z in 0 until arr.length()) {
//                                val rArr = arr.getJSONObject(z).getJSONArray("runes")
//                                for (x in 0 until rArr.length()) {
//                                    if (rArr.getJSONObject(x)
//                                            .getString("id") == perks[i].toString()
//                                    ) {
//                                        AppLogger.p(
//                                            "룬3-1:${
//                                                rArr.getJSONObject(x).getString("icon")
//                                            }"
//                                        )
//                                        runeNames[i] = Rune(
//                                            rArr.getJSONObject(x).getString("name"),
//                                            rArr.getJSONObject(x).getString("icon")
//                                        )
//                                    }
//
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//        return runeNames
//    }
//
//    private fun jsonToSpell(spellId: Long): String {
//        var spellName = ""
//        val spellString = LoLApp.getContext().assets.open("summoner.json").reader().readText()
//        val spellArray = JSONArray(spellString)
//        for (i in 0 until spellArray.length()) {
//            AppLogger.p(
//                "스펠:${
//                    spellArray.getJSONObject(i).getJSONObject("data")
//                        .getJSONObject(spellId.toString()).getJSONObject("image").getString("full")
//                }"
//            )
//            val name =
//                spellArray.getJSONObject(i).getJSONObject("data").getJSONObject(spellId.toString())
//                    .getJSONObject("image").getString("full")
//            spellName = "http://ddragon.leagueoflegends.com/cdn/12.1.1/img/spell/$name"
//        }
//        return spellName
//    }
//
//    private fun longToTeam(teamId: Long): String = if (teamId.toString() == "100") "블루" else "레드"


}