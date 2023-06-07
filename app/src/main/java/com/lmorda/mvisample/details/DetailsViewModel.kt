package com.lmorda.mvisample.details

import androidx.lifecycle.viewModelScope
import com.lmorda.mvisample.data.DataRepositoryImpl
import com.lmorda.mvisample.data.MviViewModel
import com.lmorda.mvisample.data.RepoDetailsDto
import com.lmorda.mvisample.details.DetailsContract.Event
import com.lmorda.mvisample.details.DetailsContract.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : MviViewModel<State, Event>(
    initialState = State.Initial,
) {

    private val dataRepositoryImpl = DataRepositoryImpl()

    override fun reduce(state: State, event: Event): State = when (state) {
        is State.Initial, is State.Loaded -> when (event) {
            is Event.OnLoadClick -> {
                loadRepo(id = event.id)
                State.Loading
            }
            else -> state
        }
        is State.Loading -> when (event) {
            is InternalEvent.OnLoaded -> State.Loaded(event.repoDetailsDto)
            is InternalEvent.OnLoadError -> State.LoadError(event.errorMessage)
            else -> state
        }
        else -> state
    }

    private fun loadRepo(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataRepositoryImpl.getGithubRepo(id).onSuccess { repo ->
                    push(InternalEvent.OnLoaded(repo))
                }.onFailure {
                    push(InternalEvent.OnLoadError("Error getting repo"))
                }
            } catch (ex: Exception) {
                push(InternalEvent.OnLoadError("Error getting repo"))
            }
        }
    }

    private sealed class InternalEvent : Event.Internal() {
        data class OnLoaded(val repoDetailsDto: RepoDetailsDto) : InternalEvent()
        data class OnLoadError(val errorMessage: String) : InternalEvent()
    }
}