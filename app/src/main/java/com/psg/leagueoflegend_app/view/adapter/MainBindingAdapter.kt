package com.psg.leagueoflegend_app.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psg.leagueoflegend_app.R

@BindingAdapter("image")
fun bindImage(view: ImageView, uri: String?) { //imageView에 값을 넣기위한 Adapter Layout단에서 넣어주는 값이 uri로 들어옴
    val progressDrawable = getProgressDrawable(view.context)

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.lol)

    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(view)
}

@BindingAdapter("tiericon")
fun tierIcon(view: ImageView, int: Int){
    view.setImageResource(int)
}

//@BindingAdapter("minivisible")
//fun mini(view: ImageView, boolean: Boolean){
//    if (boolean) view.visibility = View.VISIBLE else view.visibility = View.GONE
//}

@BindingAdapter("mini")
fun miniImage(view: ImageView, int: Int){
    view.setImageResource(int)
}

fun getProgressDrawable(context: Context): CircularProgressDrawable { //이미지 로딩 표시
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}