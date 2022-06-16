package com.psg.leagueoflegend_app.view.main

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psg.domain.model.League
import com.psg.domain.model.Profile
import com.psg.domain.model.Summoner
import com.psg.domain.usecase.*
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.base.BaseViewModel
import com.psg.leagueoflegend_app.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val keyUseCase: GetKeyUseCase,
    private val insertKeyUseCase: InsertKeyUseCase,
    private val summonerUseCase: GetSummonerUseCase,
    private val deleteSummonerUseCase: DeleteSummonerUseCase,
    private val deleteSummonerAllUseCase: DeleteSummonerAllUseCase,
    private val profileUseCase: GetProfileUseCase,
    private val insertProfileUseCase: InsertProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val refreshDataUseCase: RefreshDataUseCase
) : BaseViewModel() {

//    private val _eventFlow = MutableSharedFlow<Event>()
//    override val eventFlow: SharedFlow<BaseViewModel.Event> = _eventFlow.asSharedFlow()

    val summonerList: LiveData<List<Summoner>> get() = _summonerList
    private val _summonerList = MutableLiveData<List<Summoner>>()

    val profile: LiveData<Profile> get() = _profile
    private val _profile = MutableLiveData<Profile>()

    val league: LiveData<League> get() = _league
    private val _league = MutableLiveData<League>()

    private val _apiKey = MutableLiveData<String>()
    val apiKey: LiveData<String> get() = _apiKey

    val isRefresh: LiveData<Boolean> get() = _isRefresh
    private val _isRefresh = MutableLiveData<Boolean>()

    var refreshCount = 0


    override fun initViewModel() {
        viewModelScope.launch {
            _apiKey.value = keyUseCase.execute()
            _isRefresh.value = false
            summonerUpdate()
            profileUpdate()
        }
    }

    fun summonerUpdate(){
        viewModelScope.launch {
            var summonerList = listOf<Summoner>()
            withContext(Dispatchers.IO){
                summonerUseCase.execute().collect {
                    summonerList = it
                }

            }
            _summonerList.value = summonerList
        }
    }

    fun profileUpdate(){
        viewModelScope.launch {
            var profile = Profile("","","")
            withContext(Dispatchers.IO){
                profileUseCase.execute().collect {
                    profile = it
                }

            }
            _profile.value = profile
        }
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

    fun insertKey(value: String) = insertKeyUseCase.execute(value)


    fun insertProfile(profile: Profile){
        CoroutineScope(Dispatchers.IO).launch {
            deleteProfileUseCase.execute()
            insertProfileUseCase.execute(profile)
        }
    }

//    fun updateProfile(profile: Profile){
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.updateProfile(profile)
//        }
//    }

    fun deleteProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            deleteProfileUseCase.execute()
        }
    }

    fun refresh(list: List<Summoner>){
        viewModelScope.launch {
            withContext(Dispatchers.Default){
                refreshData(list)
            }
            _isRefresh.value = true
        }
    }

    private suspend fun refreshCountReset(){
        delay(5000)
        refreshCount = 0
    }

    private fun refreshData(list: List<Summoner>) {
        if (refreshCount > 0){
            return
        }
        refreshCount++
        viewModelScope.launch {
            refreshDataUseCase.execute(list).collect {
                _league.value = it
            }
            refreshCountReset()
        }

//        if (list.isNotEmpty()) {
//            for (summoner in list)
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        apiKey.value?.let {
//                            val body = repository.searchSummoner(summoner.name, it).body()
//                            val code = repository.searchSummoner(summoner.name, it).code()
//                            AppLogger.p("id는?${body?.id}")
//                            if (code == 200 && body != null) {
//                                val resLeague = repository.searchLeague(body.id, it)
//                                val resSpectator = repository.searchSpectator(body.id, it).body()
//                                val playing = resSpectator?.gameId != null
//                                AppLogger.p("게임중?$playing")
//                                val iterator = resLeague.body()?.iterator() ?: iterator { }
//                                while (iterator.hasNext()) {
//                                    val league = iterator.next()
//                                    if (league.queueType == "RANKED_SOLO_5x5") {
//                                        AppLogger.p("소환사이름:${league.summonerName},티어:${league.tier},리그포인트${league.leaguePoints},랭크:${league.rank},전적:${league.wins}승,${league.losses}패")
//                                        if (league.miniSeries != null) {
//                                            val mini = SummonerEntity.MiniSeries(
//                                                league.miniSeries.losses!!,
//                                                league.miniSeries.target!!,
//                                                league.miniSeries.wins!!,
//                                                league.miniSeries.progress!!
//                                            )
//                                            val icon =
//                                                "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"
//
//                                            repository.updateSummoner(
//                                                SummonerEntity(
//                                                    league.summonerName!!,
//                                                    body.summonerLevel.toString(),
//                                                    icon,
//                                                    league.tier!!,
//                                                    league.leaguePoints!!,
//                                                    league.rank!!,
//                                                    league.wins!!,
//                                                    league.losses!!,
//                                                    mini,
//                                                    playing
//                                                )
//                                            )
//                                            AppLogger.p("승급전중")
//                                            AppLogger.p(
//                                                "승급전:${
//                                                    league.miniSeries.progress.replace("L", "패")
//                                                        .replace("W", "승")
//                                                }"
//                                            )
//
//                                        } else {
//                                            val mini = SummonerEntity.MiniSeries(0, 0, 0, "No")
//                                            val icon =
//                                                "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/${body.profileIconId}.png"
//                                            repository.updateSummoner(
//                                                SummonerEntity(
//                                                    league.summonerName!!,
//                                                    body.summonerLevel.toString(),
//                                                    icon,
//                                                    league.tier!!,
//                                                    league.leaguePoints!!,
//                                                    league.rank!!,
//                                                    league.wins!!,
//                                                    league.losses!!,
//                                                    mini,
//                                                    playing
//                                                )
//                                            )
//                                            AppLogger.p("승급전아님")
//                                        }
////                                        toastEvent("업데이트 성공")
//
//                                    }
//                                    return@launch
//                                }
//
//                            } else {
//                                when (code) {
//                                    401 -> {
//                                        toastEvent("토큰이 인증되지 않았습니다.")
//                                        return@launch
//                                    }
//                                    403 -> {
//                                        toastEvent("토큰이 만료되었습니다.")
//                                        return@launch
//                                    }
//                                    404 -> {
//                                        toastEvent("존재하지 않는 아이디입니다.")
//                                        return@launch
//                                    }
//                                    429 -> {
//                                        AppLogger.p("너무 많은 요청")
//                                        return@launch
//                                    }
//                                }
//                                AppLogger.p(
//                                    "리스폰스에러바디:${
//                                        repository.searchSummoner(summoner.name, it).errorBody()
//                                            ?.string()
//                                    }"
//                                )
//                                AppLogger.p(
//                                    "코드:${
//                                        repository.searchSummoner(summoner.name, it).code()
//                                    }"
//                                )
//                                AppLogger.p("에러")
//                            }
//                            }
//                    }catch (e: Exception){
//                        e.printStackTrace()
//                    }
//
//                }
//        }
    }

    fun deleteSummoner(summoner: Summoner){
        CoroutineScope(Dispatchers.IO).launch {
            deleteSummonerUseCase.execute(summoner)
        }
        summonerUpdate()
        summonerUpdate()
    }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            deleteSummonerAllUseCase.execute()
        }
        summonerUpdate()
    }


//    fun getApikey() = repository.getApikey()

    fun setApikey(value:String){
        _apiKey.value = value
        insertKeyUseCase.execute(value)
    }

//    fun delApikey() {
//        repository.delApikey()
//    }



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