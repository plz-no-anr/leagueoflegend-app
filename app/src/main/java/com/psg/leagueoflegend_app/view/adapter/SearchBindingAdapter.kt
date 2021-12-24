//package com.psg.leagueoflegend_app.view.adapter
//
//import androidx.databinding.BindingAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.psg.leagueoflegend_app.LoLApp
//import com.psg.leagueoflegend_app.data.model.SearchEntity
//
//object SearchBindingAdapter {
//
//    @BindingAdapter("searchList")
//    @JvmStatic
//    fun setItems(rv: RecyclerView, items: List<SearchEntity>){
//
//        rv.adapter?.run {
//            if (this is SearchAdapter) {
//                if (items.isNotEmpty()) {
//                    this.list = items
//                }
//                this.notifyDataSetChanged()
//            }
//        } ?: run {
//            items?.let {
//                SearchAdapter(it).apply {
//                    rv.adapter = this
//                }
//            }
//        }
//    }
//}