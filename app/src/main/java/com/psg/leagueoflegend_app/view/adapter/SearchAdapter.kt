package com.psg.leagueoflegend_app.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psg.leagueoflegend_app.data.model.SearchEntity
import com.psg.leagueoflegend_app.databinding.SearchItemBinding

class SearchAdapter(private val context: Context): RecyclerView.Adapter<SearchAdapter.SearchHolder>() {
    private lateinit var binding: SearchItemBinding
    var list = mutableListOf<SearchEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        binding = SearchItemBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return SearchHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

//    interface OnItemClickListener{
//        fun onItemClick(v: View, data: SearchEntity, pos:Int)
//    }
//
//    private var listener: OnItemClickListener? = null
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        this.listener = listener
//    }

    inner class SearchHolder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SearchEntity){
            binding.item = item

        }
    }
}