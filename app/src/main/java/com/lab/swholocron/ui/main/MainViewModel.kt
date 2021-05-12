package com.lab.swholocron.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lab.swholocron.data.common.Failure
import com.lab.swholocron.data.common.Success
import com.lab.swholocron.data.people.PeopleRepository
import com.lab.swholocron.ui.arch.ArchBaseViewModel
import com.lab.swholocron.ui.arch.ArchEvent
import com.lab.swholocron.ui.arch.ArchState
import com.lab.swholocron.ui.main.MainContract.ViewEvent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val peopleRepository: PeopleRepository) : MainContract.Container,
    ArchBaseViewModel(MainContract.ViewState.EmptyState) {

    override fun getStateFlow(): StateFlow<ArchState> {
        return viewState
    }

    override fun process(event: ArchEvent) {
        when (event) {
            is ViewEvent.SearchEvent -> {
                search(event.query)
            }
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            setState { MainContract.ViewState.LoadingState(true) }

            val result = peopleRepository.search(query)

            setState { MainContract.ViewState.LoadingState(false) }

            when (result) {
                is Success -> {
                    if (result.value.isEmpty()) {
                        setState { MainContract.ViewState.EmptyState }
                    } else {
                        setState { MainContract.ViewState.DataState(result.value.to()) }
                    }
                }
                is Failure -> {
                    setState { MainContract.ViewState.ErrorState(result.reason) }
                }
            }
        }
    }

    class Factory(private val peopleRepository: PeopleRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(peopleRepository) as T
        }
    }
}