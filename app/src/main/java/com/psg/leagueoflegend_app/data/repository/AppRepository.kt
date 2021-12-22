package com.psg.leagueoflegend_app.data.repository

import com.psg.leagueoflegend_app.data.api.LeagueOfLegendAPI
import com.psg.leagueoflegend_app.data.db.dao.LoLDao

class AppRepository constructor(private val dao: LoLDao, private val api: LeagueOfLegendAPI) {

}