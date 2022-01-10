package com.psg.leagueoflegend_app.view.main

import android.content.Context
import android.os.CountDownTimer
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psg.leagueoflegend_app.LoLApp
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.data.model.ProfileEntity
import com.psg.leagueoflegend_app.data.model.SummonerEntity
import com.psg.leagueoflegend_app.data.repository.AppRepository
import com.psg.leagueoflegend_app.utils.Constants
import com.psg.leagueoflegend_app.utils.TimeCheckService
import com.psg.leagueoflegend_app.view.base.BaseViewModel
import kotlinx.coroutines.*

class MainViewModel(private val repository: AppRepository) : BaseViewModel() {

//    private val _eventFlow = MutableSharedFlow<Event>()
//    override val eventFlow: SharedFlow<BaseViewModel.Event> = _eventFlow.asSharedFlow()

    val summonerList: LiveData<List<SummonerEntity>> get() = repository.getSummoner()

    val profile: LiveData<ProfileEntity> get() = repository.getProfile()

    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> get() = _apiKey

    val isRefresh: LiveData<Boolean> get() = _isRefresh
    private var _isRefresh = MutableLiveData<Boolean>()

    init {
        _apiKey.value = repository.getApikey()
        _isRefresh.value = false
    }

//    override fun toastEvent(text: String){
//        event(SearchViewModel.Event.ShowToast(text))
//    }
//
//    private fun event(event: SearchViewModel.Event) {
//        CoroutineScope(Dispatchers.IO).launch{
//            _eventFlow.emit(event)
//        }
//    }

    fun insertProfile(profileEntity: ProfileEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteProfile()
            repository.insertProfile(profileEntity)
        }
    }

    fun updateProfile(profileEntity: ProfileEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateProfile(profileEntity)
        }
    }

    fun deleteProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteProfile()
        }
    }

    fun refresh(list: List<SummonerEntity>){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                refreshData(list)
            }
            _isRefresh.value = true
        }
    }

    private fun refreshData(list: List<SummonerEntity>) {
        if (list.isNotEmpty()) {
            for (summoner in list)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        apiKey.value?.let {
                            val body = repository.searchSummoner(summoner.name, it).body()
                            val code = repository.searchSummoner(summoner.name, it).code()
                            println("id는?${body?.id}")
                            if (code == 200 && body != null) {
                                val resLeague = repository.searchLeague(body.id, it)
                                val resSpectator = repository.searchSpectator(body.id, it).body()
                                val playing = resSpectator?.gameId != null
                                println("게임중?$playing")
                                val iterator = resLeague.body()?.iterator() ?: iterator { }
                                while (iterator.hasNext()) {
                                    val league = iterator.next()
                                    if (league.queueType == "RANKED_SOLO_5x5") {
                                        println("소환사이름:${league.summonerName},티어:${league.tier},리그포인트${league.leaguePoints},랭크:${league.rank},전적:${league.wins}승,${league.losses}패")
                                        if (league.miniSeries != null) {
                                            val mini = SummonerEntity.MiniSeries(
                                                league.miniSeries.losses!!,
                                                league.miniSeries.target!!,
                                                league.miniSeries.wins!!,
                                                league.miniSeries.progress!!
                                            )
                                            val icon =
                                                "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"

                                            repository.updateSummoner(
                                                SummonerEntity(
                                                    league.summonerName!!,
                                                    body.summonerLevel.toString(),
                                                    icon,
                                                    league.tier!!,
                                                    league.leaguePoints!!,
                                                    league.rank!!,
                                                    league.wins!!,
                                                    league.losses!!,
                                                    mini,
                                                    playing
                                                )
                                            )
                                            println("승급전중")
                                            println(
                                                "승급전:${
                                                    league.miniSeries.progress.replace("L", "패")
                                                        .replace("W", "승")
                                                }"
                                            )

                                        } else {
                                            val mini = SummonerEntity.MiniSeries(0, 0, 0, "No")
                                            val icon =
                                                "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"
                                            repository.updateSummoner(
                                                SummonerEntity(
                                                    league.summonerName!!,
                                                    body.summonerLevel.toString(),
                                                    icon,
                                                    league.tier!!,
                                                    league.leaguePoints!!,
                                                    league.rank!!,
                                                    league.wins!!,
                                                    league.losses!!,
                                                    mini,
                                                    playing
                                                )
                                            )
                                            println("승급전아님")
                                        }
//                                        toastEvent("업데이트 성공")

                                    }
                                    return@launch
                                }

                            } else {
                                when (code) {
                                    401 -> {
                                        toastEvent("토큰이 인증되지 않았습니다.")
                                        return@launch
                                    }
                                    403 -> {
                                        toastEvent("토큰이 만료되었습니다.")
                                        return@launch
                                    }
                                    404 -> {
                                        toastEvent("존재하지 않는 아이디입니다.")
                                        return@launch
                                    }
                                    429 -> {
                                        println("너무 많은 요청")
                                        return@launch
                                    }
                                }
                                println(
                                    "리스폰스에러바디:${
                                        repository.searchSummoner(summoner.name, it).errorBody()
                                            ?.string()
                                    }"
                                )
                                println(
                                    "코드:${
                                        repository.searchSummoner(summoner.name, it).code()
                                    }"
                                )
                                println("에러")
                            }
                            }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }

                }
        }
    }

    fun deleteSummoner(summonerEntity: SummonerEntity){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteSummoner(summonerEntity)
        }
    }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteSummonerAll()
        }
    }


//    fun getApikey() = repository.getApikey()

    fun setApikey(value:String){
        _apiKey.value = value
        repository.setApikey(value)
    }

    fun delApikey() {
        repository.delApikey()
    }


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

    private fun getProgressDrawable(context: Context): CircularProgressDrawable { //이미지 로딩 표시
        return CircularProgressDrawable(context).apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
    }





//    sealed class Event {
//        data class ShowToast(val text: String) : Event()
//    }


}