package com.psg.data.utils

import android.content.Context
import com.psg.domain.model.SpectatorInfo
import org.json.JSONArray

class JsonUtils(val context: Context) {

    fun jsonToMap(mapId: Long): String {
        var mapName = ""
        val mapString = context.assets.open("map.json").reader().readText()
        val mapArray = JSONArray(mapString)
        for (i in 0 until mapArray.length()) {
            AppLogger.p(
                "맵:${
                    mapArray.getJSONObject(i).getJSONObject("data").getJSONObject(mapId.toString())
                        .getString("MapName")
                }"
            )
            mapName =
                mapArray.getJSONObject(i).getJSONObject("data").getJSONObject(mapId.toString())
                    .getString("MapName")
        }
        return mapName
    }

     fun jsonToChampPath(champId: Long): String {
        var champName = ""
        if (champId == (-1).toLong()) {
            champName = "NoBan"
            return champName
        }
        val champString = context.assets.open("champion.json").reader().readText()
        val champArr = JSONArray(champString)
        for (i in 0 until champArr.length()) {
//            AppLogger.p("챔프:${champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString()).getString("name")}")
            val name =
                champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString())
                    .getJSONObject("image").getString("full")
            champName = "http://ddragon.leagueoflegends.com/cdn/12.1.1/img/champion/$name"
        }
        return champName
    }

     fun jsonToChampName(champId: Long): String {
        var champName = ""
        if (champId == (-1).toLong()) {
            champName = "NoBan"
            return champName
        }
        val champString = context.assets.open("champion.json").reader().readText()
        val champArr = JSONArray(champString)
        for (i in 0 until champArr.length()) {
//            AppLogger.p("챔프:${champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString()).getString("name")}")
            champName =
                champArr.getJSONObject(i).getJSONObject("data").getJSONObject(champId.toString())
                    .getString("name")
        }
        return champName
    }

     fun jsonToRuneStyle(perkStyle: Long): SpectatorInfo.Rune {
        var style = SpectatorInfo.Rune("", "")

        val runeString = context.assets.open("runesReforged.json").reader().readText()
        val runeArray = JSONArray(runeString)
        for (i in 0 until runeArray.length()) {
            if (runeArray.getJSONObject(i).getString("id") == perkStyle.toString()) {
                AppLogger.p("룬:${runeArray.getJSONObject(i).getString("icon")}")
                style = SpectatorInfo.Rune(
                    runeArray.getJSONObject(i).getString("name"),
                    runeArray.getJSONObject(i).getString("icon")
                )
            }
        }
        return style
    }

     fun jsonToMainRunes(perkStyle: Long, perks: Long): String {
        var runeName = ""
        val runeString = context.assets.open("runesReforged.json").reader().readText()
        val runeArray = JSONArray(runeString)
        for (j in 0 until runeArray.length()) {
            if (runeArray.getJSONObject(j).getString("id") == perkStyle.toString()) {
                val arr = runeArray.getJSONObject(j).getJSONArray("slots")
                val rArr = arr.getJSONObject(0).getJSONArray("runes")
                for (y in 0 until rArr.length()) {
                    if (rArr.getJSONObject(y).getString("id") == perks.toString()) {
                        AppLogger.p("룬2:${rArr.getJSONObject(y).getString("icon")}")
                        runeName = rArr.getJSONObject(y).getString("icon")
                    }
                }


            }
        }
        return runeName
    }

     fun jsonToRunes(perkStyle: Long, subStyle: Long, perks: List<Long>): List<SpectatorInfo.Rune> {
        val runeNames = MutableList(6) { SpectatorInfo.Rune("", "") }
        val runeString = context.assets.open("runesReforged.json").reader().readText()
        val runeArray = JSONArray(runeString)
        for (i in perks.indices) {
            if (i < 4) {
                for (j in 0 until runeArray.length()) {
                    if (runeArray.getJSONObject(j).getString("id") == perkStyle.toString()) {
                        val arr = runeArray.getJSONObject(j).getJSONArray("slots")
                        for (z in 0 until arr.length()) {
                            val rArr = arr.getJSONObject(z).getJSONArray("runes")
                            for (x in 0 until rArr.length()) {
                                if (rArr.getJSONObject(x).getString("id") == perks[i].toString()) {
                                    AppLogger.p("룬3:${rArr.getJSONObject(x).getString("icon")}")
                                    runeNames[i] = SpectatorInfo.Rune(
                                        rArr.getJSONObject(x).getString("name"),
                                        rArr.getJSONObject(x).getString("icon")
                                    )
                                }

                            }
                        }

                    }
                }
            } else {
                if (i < 6) {
                    for (j in 0 until runeArray.length()) {
                        if (runeArray.getJSONObject(j).getString("id") == subStyle.toString()) {
                            val arr = runeArray.getJSONObject(j).getJSONArray("slots")
                            for (z in 0 until arr.length()) {
                                val rArr = arr.getJSONObject(z).getJSONArray("runes")
                                for (x in 0 until rArr.length()) {
                                    if (rArr.getJSONObject(x)
                                            .getString("id") == perks[i].toString()
                                    ) {
                                        AppLogger.p(
                                            "룬3-1:${
                                                rArr.getJSONObject(x).getString("icon")
                                            }"
                                        )
                                        runeNames[i] = SpectatorInfo.Rune(
                                            rArr.getJSONObject(x).getString("name"),
                                            rArr.getJSONObject(x).getString("icon")
                                        )
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

     fun jsonToSpell(spellId: Long): String {
        var spellName = ""
        val spellString = context.assets.open("summoner.json").reader().readText()
        val spellArray = JSONArray(spellString)
        for (i in 0 until spellArray.length()) {
            AppLogger.p(
                "스펠:${
                    spellArray.getJSONObject(i).getJSONObject("data")
                        .getJSONObject(spellId.toString()).getJSONObject("image").getString("full")
                }"
            )
            val name =
                spellArray.getJSONObject(i).getJSONObject("data").getJSONObject(spellId.toString())
                    .getJSONObject("image").getString("full")
            spellName = "http://ddragon.leagueoflegends.com/cdn/12.1.1/img/spell/$name"
        }
        return spellName
    }

     fun longToTeam(teamId: Long): String = if (teamId.toString() == "100") "블루" else "레드"
}