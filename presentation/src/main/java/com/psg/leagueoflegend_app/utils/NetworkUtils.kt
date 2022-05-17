package com.psg.leagueoflegend_app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.psg.leagueoflegend_app.di.LoLApp

internal object NetworkUtils {
    fun getNetworkStatus(): LiveData<Boolean> {
        val isAvailableLiveData = MutableLiveData<Boolean>()
        val cm = LoLApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder()

        cm.registerNetworkCallback(nr.build(), object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                isAvailableLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isAvailableLiveData.postValue(false)

            }
        })
        return isAvailableLiveData
    }
}