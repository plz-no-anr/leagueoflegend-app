package com.psg.leagueoflegend_app.view.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psg.domain.model.Summoner
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.di.LoLApp

@BindingAdapter("image")
fun loadImage(view: ImageView, uri: String?) { //imageView에 값을 넣기위한 Adapter Layout단에서 넣어주는 값이 uri로 들어옴
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
fun tierIcon(view: ImageView, tier: String){
    val int = when(tier){
        "IRON" -> R.drawable.emblem_iron
        "BRONZE" -> R.drawable.emblem_bronze
        "SILVER" -> R.drawable.emblem_silver
        "GOLD" -> R.drawable.emblem_gold
        "PLATINUM" -> R.drawable.emblem_platinum
        "DIAMOND" -> R.drawable.emblem_diamond
        "MASTER" -> R.drawable.emblem_master
        "GRANDMASTER" -> R.drawable.emblem_grandmaster
        "CHALLENGER" -> R.drawable.emblem_challenger
        else -> R.drawable.lol
    }
    view.setImageResource(int)
}

//@BindingAdapter("minivisible")
//fun mini(view: ImageView, boolean: Boolean){
//    if (boolean) view.visibility = View.VISIBLE else view.visibility = View.GONE
//}

@BindingAdapter("mini1")
fun miniImage(view: ImageView, miniSeries: Summoner.MiniSeries){
    val result = if(miniSeries.progress != "No"){
        when(miniSeries.progress[0]){
            'W' -> R.drawable.ic_baseline_check_24
            'L' -> R.drawable.ic_baseline_close_24
            'N' -> R.drawable.ic_baseline_horizontal_rule_24
            else -> R.drawable.ic_baseline_horizontal_rule_24
        }
    } else {
        R.drawable.ic_baseline_horizontal_rule_24
    }

    view.setImageResource(result)
}

@BindingAdapter("mini2")
fun miniImage2(view: ImageView, miniSeries: Summoner.MiniSeries){
    val result = if(miniSeries.progress != "No"){
        when(miniSeries.progress[0]){
            'W' -> R.drawable.ic_baseline_check_24
            'L' -> R.drawable.ic_baseline_close_24
            'N' -> R.drawable.ic_baseline_horizontal_rule_24
            else -> R.drawable.ic_baseline_horizontal_rule_24
        }
    } else {
        R.drawable.ic_baseline_horizontal_rule_24
    }

    view.setImageResource(result)
}

@BindingAdapter("mini3")
fun miniImage3(view: ImageView, miniSeries: Summoner.MiniSeries){
    val result = if(miniSeries.progress != "No"){
        when(miniSeries.progress[0]){
            'W' -> R.drawable.ic_baseline_check_24
            'L' -> R.drawable.ic_baseline_close_24
            'N' -> R.drawable.ic_baseline_horizontal_rule_24
            else -> R.drawable.ic_baseline_horizontal_rule_24
        }
    } else {
        R.drawable.ic_baseline_horizontal_rule_24
    }

    view.setImageResource(result)
}

@BindingAdapter("mini4")
fun miniImage4(view: ImageView, miniSeries: Summoner.MiniSeries){
    val result = if(miniSeries.progress != "No"){
        when(miniSeries.progress[0]){
            'W' -> R.drawable.ic_baseline_check_24
            'L' -> R.drawable.ic_baseline_close_24
            'N' -> R.drawable.ic_baseline_horizontal_rule_24
            else -> R.drawable.ic_baseline_horizontal_rule_24
        }
    } else {
        R.drawable.ic_baseline_horizontal_rule_24
    }

    view.setImageResource(result)
}

@BindingAdapter("mini5")
fun miniImage5(view: ImageView, miniSeries: Summoner.MiniSeries){
    var result = 0
    result = if(miniSeries.progress != "No"){
        when(miniSeries.progress[0]){
            'W' -> R.drawable.ic_baseline_check_24
            'L' -> R.drawable.ic_baseline_close_24
            'N' -> R.drawable.ic_baseline_horizontal_rule_24
            else -> R.drawable.ic_baseline_horizontal_rule_24
        }
    } else {
        R.drawable.ic_baseline_horizontal_rule_24
    }

    view.setImageResource(result)
}

@BindingAdapter("playing")
fun isPlaying(view: ImageView, isPlaying:Boolean){
    val res = if (isPlaying) R.color.green_new else R.color.color_red
    view.setBackgroundResource(res)
}

@BindingAdapter("rune")
fun runeImage(view: ImageView, imagePath: String?) { //imageView에 값을 넣기위한 Adapter Layout단에서 넣어주는 값이 uri로 들어옴
    val asset = LoLApp.getContext().resources.assets
    val input = imagePath?.let { asset.open(it) }
    val draw = Drawable.createFromStream(input,null)
    view.setImageDrawable(draw)
}

fun getProgressDrawable(context: Context): CircularProgressDrawable { //이미지 로딩 표시
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}