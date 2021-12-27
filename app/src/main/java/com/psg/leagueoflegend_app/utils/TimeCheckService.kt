package com.psg.leagueoflegend_app.utils

import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class TimeCheckService(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
    var time = 1
    companion object{
        var isRunning = false
    }
    override fun onTick(millisUntilFinished: Long) {
        isRunning = true
        Log.d("카운트", time++.toString())
    }

    override fun onFinish() {
        isRunning = false
    }
}