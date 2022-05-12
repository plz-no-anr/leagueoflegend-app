package com.psg.leagueoflegend_app.module

import com.psg.data.utils.JsonUtils
import org.koin.dsl.module


val jsonModule = module {
    single {
        JsonUtils(get())
    }
}