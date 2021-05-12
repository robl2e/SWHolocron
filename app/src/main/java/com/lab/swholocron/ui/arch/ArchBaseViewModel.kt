package com.lab.swholocron.ui.arch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ArchBaseViewModel(initialState: ArchState) : ViewModel(),
    CoroutineScope by MainScope() {
    private val _viewState = MutableStateFlow(initialState) // private mutable state flow
    val viewState = _viewState.asStateFlow() // publicly exposed as read-only state flow

    private val currentState: ArchState
        get() = viewState.value


    protected fun setState(reduce: ArchState.() -> ArchState) {
        val newState = currentState.reduce()
        _viewState.value = newState
    }
}