package com.psg.data.mapper

import com.psg.data.model.local.ProfileEntity
import com.psg.data.model.local.SearchEntity
import com.psg.data.model.local.SummonerEntity
import com.psg.domain.model.Profile
import com.psg.domain.model.Search
import com.psg.domain.model.Summoner

fun entityToSearch(searchList: List<SearchEntity>): List<Search> {
    return searchList.toList().map {
        Search(
            it.name,
            it.date
        )
    }
}

fun searchToEntity(search: Search): SearchEntity {
    return search.let {
        SearchEntity(
            it.name,
            it.date
        )
    }
}

fun entityToProfile(profile: ProfileEntity?): Profile {
    return profile.let {
        if (it != null){
            Profile(
                it.name,
                it.level,
                it.icon
            )
        } else {
            Profile("","","")
        }

    }
}

fun profileToEntity(profile: Profile): ProfileEntity {
    return profile.let {
        ProfileEntity(
            it.name,
            it.level,
            it.icon
        )
    }
}

fun entityToSummoner(summnoners: List<SummonerEntity>): List<Summoner> {
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
            it.miniSeries?.let { it1 -> entityToMini(it1) },
            it.isPlaying
        )
    }
}

fun summonerToEntity(summoner: Summoner): SummonerEntity {
    return summoner.let {
        SummonerEntity(
            it.name,
            it.level,
            it.icon,
            it.tier,
            it.leaguePoints,
            it.rank,
            it.wins,
            it.losses,
            it.miniSeries?.let { it1 -> miniToEntity(it1) },
            it.isPlaying
        )
    }
}




fun entityToMini(minis: SummonerEntity.MiniSeries): Summoner.MiniSeries {
    return minis.let {
        Summoner.MiniSeries(
            it.losses,
            it.target,
            it.wins,
            it.progress,
        )
    }
}

fun miniToEntity(mini: Summoner.MiniSeries): SummonerEntity.MiniSeries {
    return mini.let {
        SummonerEntity.MiniSeries(
            it.losses,
            it.target,
            it.wins,
            it.progress,
        )
    }
}



