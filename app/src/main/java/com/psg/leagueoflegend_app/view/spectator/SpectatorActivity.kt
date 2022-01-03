package com.psg.leagueoflegend_app.view.spectator

import android.os.Bundle
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.databinding.ActivitySpectatorBinding
import com.psg.leagueoflegend_app.view.base.BaseActivity
import org.koin.android.ext.android.inject

class SpectatorActivity : BaseActivity<ActivitySpectatorBinding,SpectatorViewModel>(R.layout.activity_spectator) {
    override val TAG: String = SpectatorActivity::class.java.simpleName
    override val viewModel: SpectatorViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spectator)
    }
}