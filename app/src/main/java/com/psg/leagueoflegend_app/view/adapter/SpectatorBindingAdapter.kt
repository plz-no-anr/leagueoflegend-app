package com.psg.leagueoflegend_app.view.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.R

@BindingAdapter("rune")
fun runeImage(view: ImageView, imagePath: String?) { //imageView에 값을 넣기위한 Adapter Layout단에서 넣어주는 값이 uri로 들어옴
    val asset = LoLApp.getContext().resources.assets
    val input = imagePath?.let { asset.open(it) }
    val draw = Drawable.createFromStream(input,null)
    view.setImageDrawable(draw)
}

@BindingAdapter("image")
fun loadImage(view: ImageView, uri: String?){
    val progressDrawable = getProgressDrawable(view.context)

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.lol)

    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(view)
}

