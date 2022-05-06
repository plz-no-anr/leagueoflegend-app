package com.psg.data.mapper

import com.psg.data.model.local.SearchEntity
import com.psg.data.model.local.SummonerEntity
import com.psg.domain.model.Search
import com.psg.domain.model.Summoner

fun mapperToSearch(searchList: List<SearchEntity>): List<Search> {
    return searchList.toList().map {
        Search(
            it.name,
            it.date
        )
    }
}

fun mapperToSummoner(summnoners: List<SummonerEntity>): List<Summoner> {
    return summnoners.toList().map {
        Summoner(
            it.name,
            it.level,
            it.icon,
            it.tier,
            it.leaguePoints,
            it.rank,
            it.wins,
            it.losses,
            it.miniSeries?.let { it1 -> mapperToMini(it1) },
            it.isPlaying
        )
    }
}

fun mapperToMini(minis: SummonerEntity.MiniSeries): Summoner.MiniSeries {
    return minis.let {
        Summoner.MiniSeries(
            it.losses,
            it.target,
            it.wins,
            it.progress,
        )
    }
}

