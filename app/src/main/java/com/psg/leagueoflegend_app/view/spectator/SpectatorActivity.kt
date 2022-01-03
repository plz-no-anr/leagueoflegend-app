package com.psg.leagueoflegend_app.view.spectator

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.databinding.ActivitySpectatorBinding
import com.psg.leagueoflegend_app.view.adapter.SpectatorAdapter
import com.psg.leagueoflegend_app.view.base.BaseActivity
import org.koin.android.ext.android.inject

class SpectatorActivity : BaseActivity<ActivitySpectatorBinding,SpectatorViewModel>(R.layout.activity_spectator) {
    override val TAG: String = SpectatorActivity::class.java.simpleName
    override val viewModel: SpectatorViewModel by inject()
    private val adapter = SpectatorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setDisplay() {
    }

    override fun setRv() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rvPurple.layoutManager = layoutManager
        binding.rvRed.layoutManager = layoutManager
        binding.rvPurple.adapter = adapter
        binding.rvRed.adapter = adapter

    }
}