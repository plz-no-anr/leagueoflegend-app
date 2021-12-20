package com.psg.leagueoflegend_app.presentation.main

import android.os.Bundle
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.databinding.ActivityMainBinding
import com.psg.leagueoflegend_app.presentation.base.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: MainViewModel by inject()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }




//    private fun getSummoner():String{
//        var id = ""
//        CoroutineScope(Dispatchers.IO).launch {
//            val call = RetrofitClient.summonerService
//            call.getSummoner("서포터는살려주자",Constants.API_KEY).enqueue(object : Callback<Summoner> {
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