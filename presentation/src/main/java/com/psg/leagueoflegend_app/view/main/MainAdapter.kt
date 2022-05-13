package com.psg.leagueoflegend_app.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.psg.domain.model.Summoner
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.databinding.MainItemBinding
import kotlinx.android.synthetic.main.main_item.view.*

class MainAdapter(var list: List<Summoner> = mutableListOf()) :
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


    fun setData(items: List<Summoner>) {
        list = items
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, data: Summoner, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    inner class MainHolder(private val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Summoner) {
            binding.mainItem = item
            val pos = bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.iv_delete.setOnClickListener {
                    listener?.onItemClick(itemView.iv_delete, item, pos)
                }
                itemView.iv_addProfile.setOnClickListener {
                    listener?.onItemClick(itemView.iv_addProfile, item, pos)
                }

                itemView.ll_spectator.setOnClickListener {
                    listener?.onItemClick(itemView.ll_spectator, item, pos)
                }

                if (item.miniSeries?.progress != "No") {
                    itemView.ll_mini.visibility = View.VISIBLE
                } else {
                    itemView.ll_mini.visibility = View.INVISIBLE
                }

//                loadImage(itemView.iv_icon,item.icon)
//                itemView.tv_level.text = getLevels(item)
//                itemView.tv_id.text = item.name
//                tierIcon(itemView.iv_tierImg, tierIcon(item))
//                itemView.tv_tier_rank.text = getTierRank(item)
//                itemView.tv_leaguePoint.text = getLeaguePoint(item)
//                miniImage(itemView.iv_mini1,miniImage1(item))
//                miniImage(itemView.iv_mini2,miniImage2(item))
//                miniImage(itemView.iv_mini3,miniImage3(item))
//                miniImage(itemView.iv_mini4,miniImage4(item))
//                miniImage(itemView.iv_mini5,miniImage5(item))
//
//                isPlaying(itemView.iv_playing, playing(item))

            }
        }



    }




}