package com.psg.leagueoflegend_app.view.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.databinding.ActivitySearchBinding
import com.psg.leagueoflegend_app.view.adapter.SearchAdapter
import com.psg.leagueoflegend_app.view.base.BaseActivity
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import com.psg.leagueoflegend_app.view.main.MainActivity
import kotlinx.android.synthetic.main.search_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

class SearchActivity : BaseActivity<ActivitySearchBinding,SearchViewModel>(R.layout.activity_search) {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: SearchViewModel by inject()
    private val adapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setDisplay()
        setRv()
        setEventFlow()

    }

    private fun setEventFlow(){
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: BaseViewModel.Event) = when (event){
        is BaseViewModel.Event.ShowToast ->
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(this@SearchActivity,event.text, Toast.LENGTH_SHORT).show()
            }
    }


    override fun setDisplay() {
        binding.ivBackspace.setOnClickListener {
            println("뒤로가기클릭")
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
        viewModel.searchList.observe(this, {
            if (it != null){
                adapter.setData(it)
                binding.rvSearchSummoner.scrollToPosition(0) // 리사이클러뷰 아이템 추가시 위쪽으로 이동
                binding.etSearch.text = null
                println("널이 아님")
            } else {
                println("널임")
            }
        })


        adapter.setOnItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: SearchEntity, pos: Int) {
                viewModel.deleteSearch(data)
                println("아이템삭제")
                Toast.makeText(this@SearchActivity,"삭제 성공", Toast.LENGTH_SHORT).show()
            }

        })

    }








}