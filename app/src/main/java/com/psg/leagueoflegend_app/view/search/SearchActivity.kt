package com.psg.leagueoflegend_app.view.search

import android.os.Bundle
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.databinding.ActivitySearchBinding
import com.psg.leagueoflegend_app.view.base.BaseActivity
import com.psg.leagueoflegend_app.view.main.MainActivity
import org.koin.android.ext.android.inject

class SearchActivity : BaseActivity<ActivitySearchBinding,SearchViewModel>(R.layout.activity_search) {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: SearchViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDisplay()
    }

    override fun setDisplay() {
        super.setDisplay()
        binding.ivBackspace.setOnClickListener {
            finish()
        }
    }



}