package com.psg.leagueoflegend_app

import com.psg.leagueoflegend_app.LoLApp.Companion.getContext
import org.json.JSONArray
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    @Config(manifest = Config.NONE)
    fun toJson() {
        val mapString = getContext().assets.open("map.json").reader().readText()
        val mapArray = JSONArray(mapString)
        for (i in 0 until mapArray.length()){
            println("제이슨 어레이$mapArray.getJSONObject(i).getJSONArray(\"data\")")
        }
    }
}