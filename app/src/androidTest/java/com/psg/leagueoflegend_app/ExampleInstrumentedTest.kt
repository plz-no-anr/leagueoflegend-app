package com.psg.leagueoflegend_app

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.psg.leagueoflegend_app.utils.AppLogger
import org.json.JSONArray

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.psg.leagueoflegend_app", appContext.packageName)
    }

    fun toJson() {
        val mapString = LoLApp.getContext().assets.open("map.json").reader().readText()
        val mapArray = JSONArray(mapString)
        for (i in 0 until mapArray.length()){
            AppLogger.p("제이슨 어레이$mapArray.getJSONObject(i).getJSONArray(\"data\")")
        }
    }
}