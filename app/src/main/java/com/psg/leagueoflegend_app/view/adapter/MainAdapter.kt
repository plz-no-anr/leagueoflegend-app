package com.psg.leagueoflegend_app.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.databinding.MainItemBinding
import kotlinx.android.synthetic.main.main_item.view.*

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

    interface OnItemClickListener {
        fun onItemClick(v: View, data: SummonerEntity, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }



    inner class MainHolder(private val binding: MainItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: SummonerEntity){
                    binding.item = item
                    val pos = bindingAdapterPosition
                    if (pos != RecyclerView.NO_POSITION){
                        itemView.iv_delete.setOnClickListener {
                            listener?.onItemClick(itemView.iv_delete,item,pos)
                        }
                        itemView.iv_addProfile.setOnClickListener {
                            listener?.onItemClick(itemView.iv_addProfile,item,pos)
                        }

                        if (item.miniSeries?.progress != "No"){
                            itemView.ll_mini.visibility = View.VISIBLE
                        } else {
                            itemView.ll_mini.visibility = View.INVISIBLE
                        }
                        itemView.ll_spectator.setOnClickListener {
                            if (item.isPlaying){
                                // 게임중
                                val json = LoLApp.getContext().assets.open("map.json").reader().readText()
                                println("제이슨$json")
                            }
                        }
                    }
                }
            }


}