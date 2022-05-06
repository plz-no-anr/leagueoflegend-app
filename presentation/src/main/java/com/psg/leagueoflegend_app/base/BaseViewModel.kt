package com.psg.leagueoflegend_app.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    open fun toastEvent(text: String){
        event(Event.ShowToast(text))
    }

     fun event(event: Event) {
        CoroutineScope(Dispatchers.IO).launch{
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class ShowToast(val text: String) : Event()
    }

}