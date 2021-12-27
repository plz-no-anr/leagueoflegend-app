package com.psg.leagueoflegend_app.view.main

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.api.RetrofitClient
import com.psg.leagueoflegend_app.data.model.LeagueEntryDTO
import com.psg.leagueoflegend_app.data.model.Summoner
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.databinding.ActivityMainBinding
import com.psg.leagueoflegend_app.utils.Constants
import com.psg.leagueoflegend_app.utils.NetworkStatus
import com.psg.leagueoflegend_app.view.adapter.MainAdapter
import com.psg.leagueoflegend_app.view.base.BaseActivity
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import com.psg.leagueoflegend_app.view.search.SearchActivity
import com.psg.leagueoflegend_app.view.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: MainViewModel by inject()
    private val adapter = MainAdapter()
    private var refreshLock = false
    private lateinit var list : List<SummonerEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(binding.toolbar)
        setObserveList()
        setNetworkObserve()
        setRv()
        setDisplay()
        setEventFlow()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                val intent = Intent(this,SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.option -> {
                true // 드로어 레이아웃 추가
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setEventFlow(){
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: BaseViewModel.Event) = when (event){
        is BaseViewModel.Event.ShowToast ->
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@MainActivity,event.text, Toast.LENGTH_SHORT).show()
            }
    }


    override fun setDisplay(){
        binding.slMain.setOnRefreshListener {
//            if (!refreshLock) {
                viewModel.refresh(list)
//                setLock().start()
//            }
            binding.slMain.isRefreshing = false

        }

    }

    private fun setObserveList(){
        viewModel.summonerList.observe(this,{
            if (it != null){
                list = it
            }
        })
    }

//    private fun setLock(): CountDownTimer =
//          object : CountDownTimer(0,30000){
//            override fun onTick(millisUntilFinished: Long) {
//                println("타이머 동작중")
//                refreshLock = true
//            }
//
//            override fun onFinish() {
//                refreshLock = false
//            }
//
//        }


    override fun setRv() {
        adapter.setHasStableIds(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        // 리사이클러뷰 역순 정렬
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvMain.layoutManager = layoutManager
        binding.rvMain.adapter = adapter
        viewModel.summonerList.observe(this,{
            if (it != null){
                adapter.setData(it)
                println("Main: null이 아님")
            }else{
                println("Main: db값 null")
            }
        })
    }

    private fun setNetworkObserve(){
        val status = NetworkStatus(applicationContext)
        status.observe(this, Observer {
            if (it){

            } else {

            }
        })
    }



}