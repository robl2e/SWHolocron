package com.lab.swholocron.ui.detail

import com.lab.swholocron.ui.arch.ArchContainer
import com.lab.swholocron.ui.arch.ArchEvent
import com.lab.swholocron.ui.arch.ArchState
import com.lab.swholocron.ui.arch.ArchView
import com.lab.swholocron.util.observable.Observable

interface DetailContract {
    interface View : ArchView, Observable<Listener>
    interface Container : ArchContainer

    interface Listener {
        fun onEvent(event: ViewEvent)
    }

    sealed class ViewState : ArchState {
        object EmptyState : ViewState()
        data class InitialState(val name: String, val abbrevName: String) : ViewState()
        data class DataState(val uiModel: DetailUIModel) : ViewState()
        data class ErrorState(val error: Exception) : ViewState()
    }

    sealed class ViewEvent : ArchEvent {
        data class Initialize(val name: String, val uri: String) : ViewEvent()
    }
}