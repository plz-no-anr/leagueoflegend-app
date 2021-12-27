package com.psg.leagueoflegend_app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.databinding.MainItemBinding

class MainAdapter(var list: List<SummonerEntity> = mutableListOf()):
    RecyclerView.Adapter<MainAdapter.MainHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder = (MainHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.main_item, parent, false
        )
    ))

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun setData(items: List<SummonerEntity>) {
        list = items
        notifyDataSetChanged()
    }



    inner class MainHolder(private val binding: MainItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: SummonerEntity){
                    binding.item = item

                }
            }


}