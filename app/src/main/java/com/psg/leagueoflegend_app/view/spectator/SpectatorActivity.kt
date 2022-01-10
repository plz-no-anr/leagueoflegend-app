package com.psg.leagueoflegend_app.view.spectator

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.model.Rune
import com.psg.leagueoflegend_app.data.model.SpectatorInfo
import com.psg.leagueoflegend_app.databinding.ActivitySpectatorBinding
import com.psg.leagueoflegend_app.view.adapter.SpectatorAdapter
import com.psg.leagueoflegend_app.view.adapter.getProgressDrawable
import com.psg.leagueoflegend_app.view.base.BaseActivity
import kotlinx.android.synthetic.main.dialog_rune.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class SpectatorActivity : BaseActivity<ActivitySpectatorBinding,SpectatorViewModel>(R.layout.activity_spectator) {
    override val TAG: String = SpectatorActivity::class.java.simpleName
    override val viewModel: SpectatorViewModel by inject()
    private val adapterB = SpectatorAdapter()
    private val adapterR = SpectatorAdapter()
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main()

    }

    private fun main(){
        CoroutineScope(Dispatchers.Main).launch {
            load()
        }
        viewModel.isLoading.observe(this,{
            if (it){
                progressOff()
                println("로딩끝")

            } else {
                progressOn()
                println("로딩중")
            }
        })
    }

    private suspend fun load() {
        setDisplay()
        setObserve()
        setRv()
        setDisplay()
        setVm()

    }

    private fun progressOn() {
            dialog = Dialog(this).apply {
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_loading)
                show()
            }

        Glide.with(this).load(R.raw.loading)
            .apply(RequestOptions().override(500, 500))
            .into(dialog.findViewById(R.id.iv_loading) as ImageView)
    }

    private fun progressOff(){
        dialog.dismiss()
    }



    private suspend fun setVm(){
        val name = intent.getStringExtra("name")
        if (name != null) {
            viewModel.setData(name)
        }
    }

    override fun setDisplay() {
       binding.ivExit.setOnClickListener {
           finish()
       }

    }


    private fun setObserve(){
        viewModel.spectator.observe(this,{
            if (it != null){
                if(it.banChamp.isNotEmpty()) {
                val list1 = mutableListOf<String>()
                val list2 = mutableListOf<String>()

                println("밴챔프 null x ${it.map}")
                binding.tvMap.text = it.map

                    for (i in it.banChamp.indices) {
                        if (it.banChamp[i].team == "블루") {
                            list1.add(it.banChamp[i].champ)
                        } else {
                            list2.add(it.banChamp[i].champ)
                        }
                    }
                    loadImage(binding.ivBlueBan1, list1[0])
                    loadImage(binding.ivBlueBan2, list1[1])
                    loadImage(binding.ivBlueBan3, list1[2])
                    loadImage(binding.ivBlueBan4, list1[3])
                    loadImage(binding.ivBlueBan5, list1[4])

                    loadImage(binding.ivRedBan1, list2[0])
                    loadImage(binding.ivRedBan2, list2[1])
                    loadImage(binding.ivRedBan3, list2[2])
                    loadImage(binding.ivRedBan4, list2[3])
                    loadImage(binding.ivRedBan5, list2[4])
                }
            }else{
                println("밴챔프 null")
            }
        })

        viewModel.spectatorListB.observe(this,{
            if (it != null){
                for (x in it){
                    println("블루팀:${x.champName}")
                }
            }
        })

        viewModel.spectatorListR.observe(this,{
            if (it != null){
                for (x in it){
                    println("레드팀:${x.champName}")
                }
            }
        })


    }

    override fun setRv() {
        val layoutManagerB = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val layoutManagerR = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.rvBlue.layoutManager = layoutManagerB
        binding.rvRed.layoutManager = layoutManagerR
        binding.rvBlue.adapter = adapterB
        binding.rvRed.adapter = adapterR


        viewModel.spectatorListB.observe(this,{
            if (it != null){
                if (it.isNotEmpty()){
                    adapterB.setData(it)
                }
                println("size 0")
            }else{
                println("null")
            }
        })

        viewModel.spectatorListR.observe(this,{
            if (it != null){
                if (it.isNotEmpty()){
                    adapterR.setData(it)
                }
                println("size 0")
            }else{
                println("null")
            }
        })

        adapterB.setOnItemClickListener(object : SpectatorAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: SpectatorInfo, pos: Int) {
                when (v.id){
                    R.id.tv_rune ->{
                        val dialog = Dialog(this@SpectatorActivity)
                        dialog.setContentView(R.layout.dialog_rune)
                        loadAsset(dialog.iv_runeMain,dialog.tv_runeMain,data.runeStyle)
                        loadAsset(dialog.iv_rune1,dialog.tv_rune1,data.rune[0])
                        loadAsset(dialog.iv_rune2,dialog.tv_rune2,data.rune[1])
                        loadAsset(dialog.iv_rune3,dialog.tv_rune3,data.rune[2])
                        loadAsset(dialog.iv_rune4,dialog.tv_rune4,data.rune[3])
                        loadAsset(dialog.iv_subMain,dialog.tv_subMain,data.subStyle)
                        loadAsset(dialog.iv_sub1,dialog.tv_sub1,data.rune[4])
                        loadAsset(dialog.iv_sub2,dialog.tv_sub2,data.rune[5])
                        dialog.show()
                    }
                }
            }
        })

        adapterR.setOnItemClickListener(object : SpectatorAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: SpectatorInfo, pos: Int) {
                when (v.id){
                    R.id.tv_rune ->{
                        val dialog = Dialog(this@SpectatorActivity)
                        dialog.setContentView(R.layout.dialog_rune)
                        loadAsset(dialog.iv_runeMain,dialog.tv_runeMain,data.runeStyle)
                        loadAsset(dialog.iv_rune1,dialog.tv_rune1,data.rune[0])
                        loadAsset(dialog.iv_rune2,dialog.tv_rune2,data.rune[1])
                        loadAsset(dialog.iv_rune3,dialog.tv_rune3,data.rune[2])
                        loadAsset(dialog.iv_rune4,dialog.tv_rune4,data.rune[3])
                        loadAsset(dialog.iv_subMain,dialog.tv_subMain,data.subStyle)
                        loadAsset(dialog.iv_sub1,dialog.tv_sub1,data.rune[4])
                        loadAsset(dialog.iv_sub2,dialog.tv_sub2,data.rune[5])
                        dialog.show()
                    }
                }
            }
        })

    }

    private fun loadImage(view: ImageView, uri:String?){

        val progressDrawable = getProgressDrawable(view.context)

        val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.drawable.ic_baseline_clear_24)

        Glide.with(view.context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(view)
    }

    private fun loadAsset(view: ImageView, tv:TextView, rune:Rune){
        tv.text = rune.name
        val asset = LoLApp.getContext().resources.assets
        val input = rune.icon.let { asset.open(it) }
        val draw = Drawable.createFromStream(input,null)
        view.setImageDrawable(draw)
    }


}