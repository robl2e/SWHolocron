package com.lab.swholocron.ui.main

import com.lab.swholocron.ui.arch.ArchContainer
import com.lab.swholocron.ui.arch.ArchView
import com.lab.swholocron.ui.arch.ArchEvent
import com.lab.swholocron.ui.arch.ArchState
import com.lab.swholocron.util.observable.Observable
import java.lang.Exception


interface MainContract {
    interface View : ArchView, Observable<Listener>
    interface Container : ArchContainer

    interface Listener {
        fun onEvent(event: ViewEvent)
    }

    sealed class ViewState : ArchState {
        object EmptyState : ViewState()
        data class DataState(val items: List<SearchResultUIModel>) : ViewState()
        data class LoadingState(val show: Boolean = true) : ViewState()
        data class ErrorState(val error: Exception) : ViewState()
    }

    sealed class ViewEvent : ArchEvent {
        data class SearchEvent(val query: String) : ViewEvent()
        data class ItemClickEvent(val uiModel : SearchResultUIModel) : ViewEvent()
    }
}