package com.psg.leagueoflegend_app.view.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.psg.leagueoflegend_app.R
import com.psg.data.model.local.SearchEntity
import com.psg.domain.model.Search
import com.psg.leagueoflegend_app.databinding.ActivitySearchBinding
import com.psg.leagueoflegend_app.utils.AppLogger
import com.psg.leagueoflegend_app.base.BaseActivity
import com.psg.leagueoflegend_app.base.BaseViewModel
import com.psg.leagueoflegend_app.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>(R.layout.activity_search) {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: SearchViewModel by inject()
    private val adapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initViewModel()
        setObserve()
        initView()
        setRv()
//        setEventFlow()

    }

//    override fun setEventFlow(){
//        CoroutineScope(Dispatchers.IO).launch{
//            viewModel.eventFlow.collect { event -> handleEvent(event) }
//        }
//    }
//
//     override fun handleEvent(event: BaseViewModel.Event) = when (event){
//        is BaseViewModel.Event.ShowToast ->
//            CoroutineScope(Dispatchers.Main).launch {
//                makeToast(event.text)
//            }
//    }

    override fun setObserve(){
        viewModel.league.observe(this) {
            if (it != null) {
                when (it.code) {
                    0 -> {
                        makeToast("승급전 진행중")
                        viewModel.searchUpdate()
                    }
                    1 -> {
                        makeToast("등록 성공")
                        viewModel.searchUpdate()
                    }
                    2 -> makeToast("이번 시즌 솔로랭크 전적이 없거나\n 배치가 끝나지 않았습니다.")
                    3 -> makeToast("이번 시즌 전적이 존재하지 않습니다.")
                    401 -> makeToast("토큰이 인증되지 않았습니다.")
                    403 -> makeToast("토큰이 만료되었습니다.")
                    404 -> makeToast("존재하지 않는 아이디입니다.")
                    429 -> AppLogger.p("너무 많은 요청")
                    else -> makeToast("이번 시즌 전적이 존재하지 않습니다.")
                }
            }
        }
    }


    override fun initView() {
        binding.viewModel = viewModel
        binding.ivBackspace.setOnClickListener {
            AppLogger.p("뒤로가기클릭")
            finish()
        }
//        binding.etSearch.setOnKeyListener { _, keyCode, _ ->
//            when(keyCode){
//                KeyEvent.KEYCODE_ENTER -> {
//                    viewModel.saveSummoner(binding.etSearch.text.toString())
//                    true
//                }
//            }
//            true
//        }

    }


    override fun setRv(){
        adapter.setHasStableIds(true)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        // 리사이클러뷰 역순 정렬
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvSearchSummoner.layoutManager = layoutManager
        binding.rvSearchSummoner.adapter = adapter
        viewModel.searchList.observe(this) {
            if (it != null) {
                adapter.setData(it)
                binding.rvSearchSummoner.scrollToPosition(0) // 리사이클러뷰 아이템 추가시 위쪽으로 이동
                binding.etSearch.text = null
                AppLogger.p("널이 아님")
            } else {
                AppLogger.p("널임")
            }
        }


        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: Search, pos: Int) {
                viewModel.deleteSearch(data)
                AppLogger.p("아이템삭제")
                makeToast("삭제 성공")
                viewModel.searchUpdate()
            }

        })

    }








}