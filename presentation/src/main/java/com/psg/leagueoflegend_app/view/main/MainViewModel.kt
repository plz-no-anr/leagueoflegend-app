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
            _apiKey.value = keyUseCase()
            _isRefresh.value = false
            summonerUpdate()
            profileUpdate()
        }
    }

    fun summonerUpdate(){
        viewModelScope.launch {
            var summonerList = listOf<Summoner>()
            withContext(Dispatchers.IO){
                summonerUseCase().collect {
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
                profileUseCase().collect {
                    profile = it
                }

            }
            _profile.value = profile
        }
    }



    fun insertKey(value: String) = insertKeyUseCase(value)


    fun insertProfile(profile: Profile){
        CoroutineScope(Dispatchers.IO).launch {
            deleteProfileUseCase()
            insertProfileUseCase(profile)
        }
    }

    fun deleteProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            deleteProfileUseCase()
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
            refreshDataUseCase(list).collect {
                _league.value = it
            }
            refreshCountReset()
        }

    }

    fun deleteSummoner(summoner: Summoner){
        CoroutineScope(Dispatchers.IO).launch {
            deleteSummonerUseCase(summoner)
        }
        summonerUpdate()
        summonerUpdate()
    }

    fun deleteAll(){
        CoroutineScope(Dispatchers.IO).launch {
            deleteSummonerAllUseCase()
        }
        summonerUpdate()
    }



    fun setApikey(value:String){
        _apiKey.value = value
        insertKeyUseCase(value)
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


}