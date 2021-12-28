package com.psg.leagueoflegend_app.view.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.api.RetrofitClient
import com.psg.leagueoflegend_app.data.model.*
import com.psg.leagueoflegend_app.databinding.ActivityMainBinding
import com.psg.leagueoflegend_app.utils.Constants
import com.psg.leagueoflegend_app.utils.NetworkStatus
import com.psg.leagueoflegend_app.view.adapter.MainAdapter
import com.psg.leagueoflegend_app.view.adapter.SearchAdapter
import com.psg.leagueoflegend_app.view.base.BaseActivity
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import com.psg.leagueoflegend_app.view.search.SearchActivity
import com.psg.leagueoflegend_app.view.search.SearchViewModel
import kotlinx.android.synthetic.main.dialog_settingkey.*
import kotlinx.android.synthetic.main.header_navi.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main),NavigationView.OnNavigationItemSelectedListener {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: MainViewModel by inject()
    private val adapter = MainAdapter()
//    private var refreshLock = false
    private lateinit var list : List<SummonerEntity>
    private var profile : ProfileEntity = ProfileEntity("","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar(binding.toolbar)
        setObserve()
//        setNetworkObserve()
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
                binding.dlMain.openDrawer(Gravity.RIGHT)
                if (profile.icon != ""){
                    binding.nvMain.tv_name.text = profile.name
                    binding.nvMain.tv_level.text = "LV: ${profile.level}"
                    viewModel.bindImage(binding.nvMain.iv_image,profile.icon)
                } else {
                    binding.nvMain.tv_name.text = "이름"
                    binding.nvMain.tv_level.text = "LV: "
                    viewModel.bindImage(binding.nvMain.iv_image,"")
                }
                true // 드로어 레이아웃 추가
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if (binding.dlMain.isDrawerOpen(Gravity.RIGHT)) {
            binding.dlMain.closeDrawer(Gravity.RIGHT)
        } else {
            super.onBackPressed()
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


    @SuppressLint("SetTextI18n")
    override fun setDisplay(){
        binding.slMain.setOnRefreshListener {
            val refresh = viewModel.refresh(list)
            if (refresh){
                binding.slMain.isRefreshing = false
            }
        }
        binding.tvDeleteAll.setOnClickListener {
            viewModel.deleteAll()
            Toast.makeText(this@MainActivity,"전체 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.dlMain.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)

        binding.nvMain.setNavigationItemSelectedListener(this)

    }


    private fun setObserve(){
        viewModel.summonerList.observe(this,{
            if (it != null){
                list = it
            } else {
                println("리스트 null")
            }
        })

        viewModel.profile.observe(this,{
            profile = if (it != null){
                it
            } else {
                println("프로필 null")
                ProfileEntity("","","")
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

        adapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: SummonerEntity, pos: Int) {
                if (v.id == R.id.iv_delete){
                    viewModel.deleteSummoner(data)
                    println("아이템삭제")
                    Toast.makeText(this@MainActivity,"삭제 성공", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.insertProfile(ProfileEntity(data.name,data.level,data.icon))
                    println("프로필변경")
                    Toast.makeText(this@MainActivity,"프로필 변경 성공", Toast.LENGTH_SHORT).show()

                }

            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.item_info -> {
                println("정보버튼")
                true
            }
            R.id.item_key -> {
                println("키버튼")
                setKeyDialog()
                true // 드로어 레이아웃 추가
            }
            R.id.item_delete -> {
                println("삭제버튼")
                viewModel.deleteProfile()
                binding.dlMain.closeDrawer(Gravity.RIGHT)
                true // 드로어 레이아웃 추가
            }
            else -> {
                println("else")
                true
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setKeyDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_settingkey)
        val params = dialog.window?.attributes
        params?.width = 1000
        params?.height = 1000
        viewModel.apiKey.observe(this,{
            if (it != null){
                dialog.tv_key.text = "키: $it"
            } else {
                dialog.tv_key.text = "키: "
            }
        })

        dialog.show()
        dialog.tv_key.setOnClickListener {
            if (dialog.tv_key.text.isNotEmpty()){
                deleteKeyDialog()
                dialog.tv_key.text = ""
            }

        }
        dialog.btn_confirm.setOnClickListener {
            val key = dialog.et_key.text.toString().replace(" ","")
            if (key.isNotEmpty()) {
                viewModel.setApikey(key)
                dialog.dismiss()
            } else {
                Toast.makeText(this@MainActivity,"키를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun deleteKeyDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("키 삭제").setMessage("정말로 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { dialog, _ ->
            viewModel.delApikey()
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

//    private fun setNetworkObserve(){
//        val status = NetworkStatus(applicationContext)
//        status.observe(this, Observer {
//            if (it){
//
//            } else {
//
//            }
//        })
//    }




}