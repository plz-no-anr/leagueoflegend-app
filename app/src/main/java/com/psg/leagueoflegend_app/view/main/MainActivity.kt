package com.psg.leagueoflegend_app.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.api.RetrofitClient
import com.psg.leagueoflegend_app.data.model.LeagueEntryDTO
import com.psg.leagueoflegend_app.data.model.Summoner
import com.psg.leagueoflegend_app.databinding.ActivityMainBinding
import com.psg.leagueoflegend_app.utils.Constants
import com.psg.leagueoflegend_app.view.base.BaseActivity
import com.psg.leagueoflegend_app.view.search.SearchActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(binding.toolbar)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                val intent = Intent(this,SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.option -> {
                getSum()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun getSum(){
        var id: String
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.summonerService
            id = call.getSummoner("서포터는살려주자", Constants.API_KEY).execute().body()?.id.toString()
            call.getLeague(id,Constants.API_KEY).enqueue(object : Callback<Set<LeagueEntryDTO>>{
                override fun onResponse(
                    call: Call<Set<LeagueEntryDTO>>,
                    response: Response<Set<LeagueEntryDTO>>
                ) {
                    val iterator = response.body()?.iterator() ?: iterator {  }
                    while (iterator.hasNext()){
                        val league = iterator.next()
                        if (league.queueType == "RANKED_SOLO_5x5") {
                            println("소환사이름:${league.summonerName},티어:${league.tier},랭크:${league.rank},전적:${league.wins}승,${league.losses}패")
//                            println("승급전:${league.miniSeries?.progress}")
                            println("승급전:${league.miniSeries?.progress?.replace("L","패")?.replace("W","승")}")
                        }
                    }
                    println("에러정보${response.errorBody()?.string()}")
                }

                override fun onFailure(call: Call<Set<LeagueEntryDTO>>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }


//    private fun getSummoner():String{
//        var id = ""
//        CoroutineScope(Dispatchers.IO).launch {
//            val call = RetrofitClient.summonerService
//            call.getSummoner("서포터는살려주자", Constants.API_KEY).enqueue(object : Callback<Summoner> {
//                override fun onResponse(call: Call<Summoner>, response: Response<Summoner>) {
//                    println("소환사정보:${response.body() as Summoner}")
//                    println("에러정보${response.errorBody()?.string()}")
//                }
//
//                override fun onFailure(call: Call<Summoner>, t: Throwable) {
//                    t.printStackTrace()
//                }
//
//            })
//        }
//        return id
//    }
//
//    private fun getLeague(){
//        CoroutineScope(Dispatchers.IO).launch {
//            val call = RetrofitClient.summonerService
//            call.getLeague("6jhtWqCOUfnNqpaZRn1lNbXrH1TCkwPNETy6X836fG3LY7s",Constants.API_KEY).enqueue(object : Callback<Set<LeagueEntryDTO>>{
//                override fun onResponse(
//                    call: Call<Set<LeagueEntryDTO>>,
//                    response: Response<Set<LeagueEntryDTO>>
//                ) {
//                    val iterator = response.body()?.iterator() ?: iterator {  }
//                    while (iterator.hasNext()){
//                        val league = iterator.next()
//                        if (league.queueType == "RANKED_SOLO_5x5") {
//                            println("전적${league.wins}승,${league.losses}패")
//                        }
//                    }
////                    println("리그정보:${response.body()!!}")
//                    println("에러정보${response.errorBody()?.string()}")
//                }
//
//                override fun onFailure(call: Call<Set<LeagueEntryDTO>>, t: Throwable) {
//                    t.printStackTrace()
//                }
//
//            })
//        }
//    }


}