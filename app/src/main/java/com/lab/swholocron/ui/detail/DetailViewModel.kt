package com.lab.swholocron.ui.detail

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lab.swholocron.data.common.Failure
import com.lab.swholocron.data.common.Success
import com.lab.swholocron.data.people.PeopleRepository
import com.lab.swholocron.ui.arch.ArchBaseViewModel
import com.lab.swholocron.ui.arch.ArchEvent
import com.lab.swholocron.ui.arch.ArchState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val peopleRepository: PeopleRepository
) : DetailContract.Container,
    ArchBaseViewModel(DetailContract.ViewState.EmptyState) {

    override fun getStateFlow(): StateFlow<ArchState> {
        return viewState
    }

    override fun process(event: ArchEvent) {
        when (event) {
            is DetailContract.ViewEvent.Initialize -> {
                getInitialState(event.name)
                getPerson(getId(event.uri))
            }
        }
    }

    private fun getId(uri: String): String? {
        val uriObj = Uri.parse(uri)
        return uriObj.pathSegments.last()
    }

    private fun getInitialState(name: String) {
        setState { DetailContract.ViewState.InitialState(name, abbrevName = name.substring(0, 2)) }
    }

    private fun getPerson(id: String?) {
        viewModelScope.launch {
            id?.let {
                when (val result = peopleRepository.get(id)) {
                    is Success -> {
                        setState { DetailContract.ViewState.DataState(result.value.to()) }
                    }
                    is Failure -> {
                        setState { DetailContract.ViewState.ErrorState(result.reason) }
                    }
                }
            }
                ?: setState { DetailContract.ViewState.ErrorState(IllegalArgumentException("Unknown person")) }
        }
    }

    class Factory(
        private val peopleRepository: PeopleRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(peopleRepository) as T
        }
    }
}