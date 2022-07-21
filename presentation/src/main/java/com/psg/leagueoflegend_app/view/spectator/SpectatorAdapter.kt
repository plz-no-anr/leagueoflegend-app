package com.psg.leagueoflegend_app.view.spectator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.psg.leagueoflegend_app.R
import com.psg.domain.model.SpectatorInfo
import com.psg.leagueoflegend_app.databinding.SpectatorItemBinding
import kotlinx.android.synthetic.main.spectator_item.view.*

class SpectatorAdapter(var list: List<SpectatorInfo> = mutableListOf()):
    RecyclerView.Adapter<SpectatorAdapter.SpectatorHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpectatorHolder = (SpectatorHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.spectator_item, parent, false
        )
    ))

    override fun onBindViewHolder(holder: SpectatorHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun setData(items: List<SpectatorInfo>) {
        list = items
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, data: SpectatorInfo, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    inner class SpectatorHolder(private val binding: SpectatorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SpectatorInfo){
            binding.spectatorItem = item
            val pos = bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION){
                itemView.tv_rune.setOnClickListener {
                    listener?.onItemClick(itemView.tv_rune,item,pos)
                }
            }
        }
    }


}