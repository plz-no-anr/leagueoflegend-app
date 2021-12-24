package com.psg.leagueoflegend_app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.databinding.SearchItemBinding
import kotlinx.android.synthetic.main.search_item.view.*

class SearchAdapter(var list: List<SearchEntity> = mutableListOf()) :
    RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder = (SearchHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.search_item, parent, false
        )
    ))

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    fun setData(items: List<SearchEntity>) {
        list = items
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, data: SearchEntity, pos: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    inner class SearchHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchEntity) {
            binding.searchItem = item

            val pos = bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION){
                itemView.iv_remove.setOnClickListener {
                    listener?.onItemClick(itemView.iv_remove,item,pos)
                }
            }
        }


    }
}