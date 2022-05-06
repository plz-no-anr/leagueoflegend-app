package com.psg.leagueoflegend_app.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.psg.leagueoflegend_app.R
import com.psg.data.model.local.SummonerEntity
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

                        itemView.ll_spectator.setOnClickListener {
//                            if (item.isPlaying) {
                                listener?.onItemClick(itemView.ll_spectator, item, pos)
//                            }
                        }

                        if (item.miniSeries?.progress != "No"){
                            itemView.ll_mini.visibility = View.VISIBLE
                        } else {
                            itemView.ll_mini.visibility = View.INVISIBLE
                        }

                    }
                }
            }


}